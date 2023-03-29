package onthemars.back.nft.repository;

import onthemars.back.nft.entity.Nft;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends PagingAndSortingRepository<Nft, String> {

}
