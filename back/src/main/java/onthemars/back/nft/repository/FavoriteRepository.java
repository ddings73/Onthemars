package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.nft.entity.Favorite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends PagingAndSortingRepository<Favorite, Long> {

    List<Favorite> findByMember_AddressAndActivated(String address, Boolean activated,
        Pageable pageable);
    Optional<Favorite> findByMember_AddressAndTransaction_Id(String address, Long id);

}
