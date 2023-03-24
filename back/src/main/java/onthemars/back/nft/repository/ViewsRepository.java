package onthemars.back.nft.repository;

import onthemars.back.nft.entity.Views;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewsRepository extends PagingAndSortingRepository<Views, Long> {

}
