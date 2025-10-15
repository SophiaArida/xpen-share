package com.expenshare.service;

import com.expenshare.exception.ValidationException;
import com.expenshare.model.dto.expense.*;
import com.expenshare.model.entity.*;
import com.expenshare.model.mapper.*;
import com.expenshare.repository.facade.ExpenseRepositoryFacade;
//import com.expenshare.event.KafkaProducer;
import jakarta.inject.Singleton;
//import org.apache.kafka.common.quota.ClientQuotaAlteration;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class ExpenseService {

    private final ExpenseMapper expenseMapper;
    private final ExpenseShareMapper shareMapper;
    private final ExpenseRepositoryFacade facade;
//    private final KafkaProducer kafkaProducer;
    private final GroupService groupService;
    private final UserService userService;

    //    public ExpenseService(ExpenseMapper expenseMapper, ExpenseShareMapper shareMapper,
//                          ExpenseRepositoryFacade facade, KafkaProducer kafkaProducer) {
    public  ExpenseService(ExpenseMapper expenseMapper, ExpenseShareMapper shareMapper,
                           ExpenseRepositoryFacade facade, GroupService groupService, UserService userService) {
        this.expenseMapper = expenseMapper;
        this.shareMapper = shareMapper;
        this.facade = facade;
//        this.kafkaProducer = kafkaProducer;
        this.groupService = groupService;
        this.userService = userService;
    }

    public ExpenseDto createExpense(CreateExpenseRequest req) {

        // 1. Get group members
        GroupEntity group = groupService.getGroupEntity(req.getGroupId());
        List<Long> memberIds = group.getMembers().stream()
                .map(gm -> gm.getUser().getUserId())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());


        if (memberIds.isEmpty()) {
            throw new ValidationException("No members found for group " + req.getGroupId());
        }

        int memberCount = memberIds.size();
        System.out.println("expenseService.createExpense: Done Step 1");

        // 2. Each person’s equal share
        List<ShareDto> split = new ArrayList<>();

        switch (req.getSplitType()) {
            case EQUAL -> {
                BigDecimal equalShare = req.getAmount()
                        .divide(BigDecimal.valueOf(memberCount), 2, BigDecimal.ROUND_HALF_UP);

                for (Long userId : memberIds) {
                    BigDecimal share;
                    if (userId.equals(req.getPaidBy())) {
                        // Paid everything → owes only their own part
                        share = equalShare.subtract(req.getAmount());
                    } else {
                        share = equalShare;
                    }
                    share = share.setScale(2, BigDecimal.ROUND_HALF_UP);

                    ShareDto dto = new ShareDto();
                    dto.setUserId(userId);
                    dto.setShare(share);
                    split.add(dto);
                }
            }

            case EXACT -> {
                if (req.getShares() == null || req.getShares().isEmpty()) {
                    throw new ValidationException("Shares must be provided for EXACT split type.");
                }

                BigDecimal total = req.getShares().stream()
                        .map(ShareDto::getShare)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (total.compareTo(req.getAmount()) != 0) {
                    throw new ValidationException("Sum of shares must equal total amount.");
                }

                split.addAll(req.getShares());
            }

            case PERCENT -> {
                if (req.getShares() == null || req.getShares().isEmpty()) {
                    throw new ValidationException("Shares must be provided for PERCENT split type.");
                }

                BigDecimal totalPercent = req.getShares().stream()
                        .map(ShareDto::getShare)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (totalPercent.compareTo(BigDecimal.valueOf(100)) != 0) {
                    throw new ValidationException("Total percentage must be 100.");
                }

                for (ShareDto s : req.getShares()) {
                    BigDecimal amount = req.getAmount()
                            .multiply(s.getShare())
                            .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                    ShareDto dto = new ShareDto();
                    dto.setUserId(s.getUserId());
                    dto.setShare(amount);
                    split.add(dto);
                }
            }

            default -> throw new ValidationException("Unsupported split type: " + req.getSplitType());
        }
        System.out.println("expenseService.createExpense: Done Step 2");

        // 3. Save expense
        ExpenseEntity entity = new ExpenseEntity();
        entity.setGroup(groupService.getGroupEntity(req.getGroupId()));
        entity.setPaidBy(req.getPaidBy());
        entity.setAmount(req.getAmount());
        entity.setDescription(req.getDescription());
        entity.setSplitType(req.getSplitType());
        entity.setCreatedAt(Instant.now());

        List<ExpenseShareEntity> shareEntities = split.stream()
                .map(dto -> {
                    ExpenseShareEntity s = new ExpenseShareEntity();
                    s.setUser(userService.getUserEntity(dto.getUserId()));
                    s.setShareAmount(dto.getShare());
                    return s;
                })
                .toList();

        ExpenseEntity saved = facade.saveWithShares(entity, shareEntities);

        // 4. Build response
        ExpenseDto response = new ExpenseDto();
        response.setExpenseId(saved.getId());
        response.setGroupId(saved.getGroup().getGroupId());
        response.setPaidBy(saved.getPaidBy());
        response.setAmount(saved.getAmount());
        response.setDescription(saved.getDescription());
        response.setSplit(split);
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
    public Optional<ExpenseDto> getExpense(Long id) {
        Optional<ExpenseEntity> entityOpt = facade.findById(id);
        if (entityOpt.isEmpty()) return Optional.empty();

        List<ExpenseShareEntity> shares = facade.findSharesByExpenseId(id);
        List<ShareDto> shareDtos = shares.stream()
                .map(shareMapper::toDto)
                .toList();

        return Optional.of(expenseMapper.toDto(entityOpt.get(), shareDtos));
    }
}
