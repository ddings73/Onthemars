# [특화 PRJ 메타버스 도메인] On The Mars

### CryptoKitties 스타일의 Solidity강의인 [크립토좀비](https://cryptozombies.io/) 사이트 학습
    - NFT 유전코드에 따른 이미지 생성 시에 파츠별 조합예시 학습
        - ex: 8356281049284737 가 주어진다면 아래와 같은 형식으로 구분
        - 83 : 83 % 7 + 1 => 7, 머리파츠는 7번
        - 56 : 56 % 11 + 1=> 2, 눈 파츠는 2번
        - 28 : 28 % 6 + 1 => 5, 셔츠 파츠는 5번
    - NFT 이미지 조합을 유한하게한다면 몇가지 파츠로 제한할 것인가?

### Unity 멀티플레이 서버인 Photon Engine에 대한 학습, Photon Cloud / Photon Server 중에 어떤걸 써야하는가? 
    - Photon 서버는 Windows용.. 
    - EC2 Ubuntu서버와 Windows서버 따로구성? 
        - 백엔드, 프론트, Ganache 등등 => Ubuntu
        - Photon => Windows