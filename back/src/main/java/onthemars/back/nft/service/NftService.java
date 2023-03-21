package onthemars.back.nft.service;

import onthemars.back.nft.repository.FavoriteRepository;
import onthemars.back.nft.repository.NftRepository;
import onthemars.back.nft.repository.NftT2Repository;
import onthemars.back.nft.repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NftService {

    private final NftRepository nftRepository;
    private final NftT2Repository nftT2Repository;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;

    @Autowired
    public NftService(NftRepository nftRepository, NftT2Repository nftT2Repository,
        FavoriteRepository favoriteRepository, ViewsRepository viewsRepository) {
        this.nftRepository = nftRepository;
        this.nftT2Repository = nftT2Repository;
        this.favoriteRepository = favoriteRepository;
        this.viewsRepository = viewsRepository;
    }
}
