package com.myproject.springboot_advenced.mapper;

import com.myproject.springboot_advenced.dto.CreateTransactionRequest;
import com.myproject.springboot_advenced.dto.TransactionDTO;
import com.myproject.springboot_advenced.entity.Order;
import com.myproject.springboot_advenced.entity.Transaction;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper extends BaseMapper<Transaction, TransactionDTO> {

    Transaction toEntity(TransactionDTO transactionDTO);

    Transaction fromCreateDto(CreateTransactionRequest transactionCreateDTO);

    @Mapping(target = "orderId", source = "order")
    TransactionDTO toDto(Transaction transaction);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Transaction entity, TransactionDTO dto);

    default Order map(Long orderId) {
        if (orderId == null) return null;
        Order order = new Order();
        order.setId(orderId);
        return order;
    }

     default Long map(Order order) {
        if (order == null) return null;
        return order.getId();
    }


}
