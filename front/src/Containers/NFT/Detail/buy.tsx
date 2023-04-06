import { Input, Modal } from 'antd';
import axios from 'axios';
import { ButtonDiv } from 'component/button/Button';
import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import styles from './buy.module.scss';
import { baseURL } from 'apis/baseApi';
import { SaleContract } from 'apis/ContractAddress';

export function BuyDiv(props: {
  nickname: string;
  price: number;
  activated: boolean;
  transactionId: number;
  isOwner: boolean;
  tokenId: string;
  ownerAddress: string;
}) {
  const [price, setPrice] = useState(props.price);
  const [activated, setActivated] = useState(props.activated);
  const [userCheck, setUserCheck] = useState(props.isOwner);
<<<<<<< HEAD
=======
  const transactionId = props.transactionId;
  const tokenId = parseInt(props.tokenId);
  const ownerAddress = props.ownerAddress;
>>>>>>> 74a1d761b65de93ecb04dab4180386a80f162987

  const [loadingBuy, setLoadingBuy] = useState(false);
  const [loadingList, setLoadingList] = useState(false);
  const [loadingCancel, setLoadingCancel] = useState(false);

  const address = sessionStorage.getItem('address');

  const buyToast = Swal.mixin({
    toast: true,
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      Swal.showLoading();
      if (!loadingBuy) Swal.stopTimer();
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    },
  });
  const listToast = Swal.mixin({
    toast: true,
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      Swal.showLoading();
      if (!loadingList) Swal.stopTimer();
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    },
  });
  const cancelToast = Swal.mixin({
    toast: true,
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      Swal.showLoading();
      if (!loadingCancel) Swal.stopTimer();
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    },
    willClose: () => { },
  });
  if (loadingBuy) {
    buyToast.fire({
      title: '구매중입니다.',
    });
  }
  if (loadingList) {
    listToast.fire({
      title: '판매를 등록하고 있습니다.',
    });
  }
  if (loadingCancel) {
    cancelToast.fire({
      title: '구매를 취소하고 있습니다',
    });
  }
  // 구매 모달
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const cancelModal = () => {
    setIsModalOpen(false);
  };

  // List 모달
  const [isListModalOpen, setIsListModalOpen] = useState(false);
  const showListModal = () => {
    setIsListModalOpen(true);
  };
  const cancelListModal = () => {
    setIsListModalOpen(false);
  };
  // List Cancle 모달
  const [isListCancleModalOpen, setIsListCancleModalOpen] = useState(false);
  const showListCancelModal = () => {
    setIsListCancleModalOpen(true);
  };
  const closeCancelListModal = () => {
    setIsListCancleModalOpen(false);
  };

  // 구매 버튼
<<<<<<< HEAD
  function buyButton() {
    axios({
      method: 'post',
      url: baseURL + `/nft/history/sale/${transactionId}`,
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    }).then((res) => {
      console.log(res.data);
      setPrice(-1)
      setUserCheck((prev) => !prev)
    });
    setIsModalOpen(false)
  };
=======
  async function buyButton() {
    const saleId = await SaleContract.methods.getCurrentSaleOfMARS_NFT(tokenId).call();
    setLoadingBuy(true);
    SaleContract.methods
      .buyNow(saleId, address)
      .send({
        from: address,
        gasPrice: '0',
      })
      .then(() => {
        axios({
          method: 'post',
          url: baseURL + `/nft/history/sale/${transactionId}`,
          headers: {
            Authorization: sessionStorage.getItem('accessToken'),
          },
        }).then((res) => {
          setPrice(-1);
          setUserCheck((prev) => !prev);
          setActivated((prev) => !prev);
          setLoadingBuy(false);
          Swal.fire('구매가 완료되었습니다.', '', 'success');
        });
      });

    setIsModalOpen(false);
  }
>>>>>>> 74a1d761b65de93ecb04dab4180386a80f162987

  // List 취소 버튼
  async function cancleButton() {
    const saleId = await SaleContract.methods.getCurrentSaleOfMARS_NFT(tokenId).call();
    setLoadingCancel(true);
    SaleContract.methods
      .cancelSale(saleId)
      .send({
        from: address,
        gasPrice: '0',
      })
      .then(() => {
        axios({
          method: 'post',
          url: baseURL + `/nft/history/cancel/${transactionId}`,
          headers: {
            Authorization: sessionStorage.getItem('accessToken'),
          },
        }).then((res) => {
          setPrice(-1);
          setActivated((prev) => !prev);
          setLoadingCancel(false);
          Swal.fire('구매가 취소되었습니다.', '', 'success');
        });
      });

    setIsListCancleModalOpen(false);
  }

  // List 버튼
  const [listPrice, setListPrice] = useState('');
  function listData(listPrice: string) {
    setLoadingList(true);
    // 판매 solidity 등록
    SaleContract.methods
      .createSale(tokenId, ownerAddress, parseInt(listPrice))
      .send({
        from: address,
        gasPrice: '0',
      })
      .then(() => {
        axios({
          method: 'post',
          url: baseURL + `/nft/history/listing`,
          data: {
            transactionId: transactionId,
            price: listPrice,
          },
          headers: {
            Authorization: sessionStorage.getItem('accessToken'),
          },
        }).then((res) => {
          setPrice(Number(listPrice));
          setActivated((prev) => !prev);
          setLoadingList(false);
          Swal.fire('판매가 등록되었습니다.', '', 'success');
        });
      });

    setIsListModalOpen(false);
  }

  return (
    <div className={styles.container}>
      <div className={styles.subText}>Current Price</div>
      {price === -1 ? (
        <div className={styles.price}> -</div>
      ) : (
        <div className={styles.price}>{price.toLocaleString()} O₂</div>
      )}
      <div style={{ display: 'flex', justifyContent: 'space-around' }}>
        {/* 구매가능한 토큰이고, 내가 list 해놓은게 아니면 구매가능 */}
        {activated && !userCheck ? (
          // 구매 가능
          <div onClick={showModal} style={{ width: '48%' }}>
            <ButtonDiv disabled={false} text={'Buy now'} loading={loadingBuy} icon={'Buy'} />
          </div>
        ) : (
          // 구매 안될때
          <div style={{ width: '48%' }}>
            <ButtonDiv disabled={true} text={'Buy now'} icon={'Buy'} />
          </div>
        )}

        {/* NFT를 만든 사람과 접속한 유저가 같은 사람인지 */}
        {userCheck ? (
          // 내가 민팃한 nft일때
          <>
            {activated ? (
              <div onClick={showListCancelModal} style={{ width: '48%' }}>
                <ButtonDiv
                  disabled={false}
                  text={'Cancel'}
                  loading={loadingList}
                  color={'white'}
                  icon={'List'}
                />
              </div>
            ) : (
              <div onClick={showListModal} style={{ width: '48%' }}>
                <ButtonDiv
                  disabled={false}
                  text={'List'}
                  loading={loadingList}
                  color={'white'}
                  icon={'List'}
                />
              </div>
            )}
          </>
        ) : (
          // 남이한것일때
          <div style={{ width: '48%' }}>
            <ButtonDiv disabled={true} text={'List'} color={'white'} icon={'List'} />
          </div>
        )}
      </div>
      {/* 구매 모달 */}
      <Modal open={isModalOpen} centered onCancel={cancelModal} footer={null}>
        <div className={styles.modalTitle}>구매하시겠습니까?</div>
        <div onClick={buyButton}>
          <ButtonDiv text={'확인'} />
        </div>
      </Modal>

      {/* List 모달 */}
      <Modal open={isListModalOpen} centered onCancel={cancelListModal} footer={null}>
        <div className={styles.modalTitle}>List Price</div>
        <div className={styles.modalINput}>
          <Input id="listPrice" onChange={(e) => setListPrice(e.target.value)} />
          <div className={styles.modalList} onClick={() => listData(listPrice)}>
            <ButtonDiv text={'확인'} />
          </div>
        </div>
      </Modal>

      {/* 취소 모달 */}
      <Modal open={isListCancleModalOpen} centered onCancel={closeCancelListModal} footer={null}>
        <div className={styles.modalTitle}>판매를 취소 하시겠습니까?</div>
        <div onClick={cancleButton}>
          <ButtonDiv text={'확인'} />
        </div>
      </Modal>
    </div>
  );
}
