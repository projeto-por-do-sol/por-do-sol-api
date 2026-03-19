package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Item;
import app.entity.Quiosque;

public interface ItemRepository extends JpaRepository<Item, Long> {

	public List<Item> findByQuiosque(Quiosque quiosque);
	
	public Optional<Item> findByIdAndQuiosque(long id, Quiosque quiosque);
	
	
}
