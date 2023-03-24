package onthemars.back.nft.repository;

import onthemars.back.nft.entity.Favorite;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends PagingAndSortingRepository<Favorite, Long> {

}
