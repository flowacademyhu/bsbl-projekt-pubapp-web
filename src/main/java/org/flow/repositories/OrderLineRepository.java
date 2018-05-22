package org.flow.repositories;

import org.flow.models.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long>{
}
