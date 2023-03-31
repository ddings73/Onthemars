import { Input, Modal } from 'antd';
import axios from 'axios';
import { ButtonDiv } from 'component/button/Button'
import { useEffect, useState } from 'react';


import styles from './buy.module.scss'
import { baseURL } from 'apis/baseApi';

export function BuyDiv(props: { nickname: string, price: number, activated: boolean }) {
  const price = props.price
  const activated = props.activated
  const [userCheck, setUserCheck] = useState<boolean>(false);
  const nickname = props.nickname

  useEffect(() => {

    axios({
      method: 'get',
      url: baseURL + `/user/${sessionStorage.getItem('address')}`,
    }).then((res) => {
      if (nickname === res.data.user.nickname) { setUserCheck(true) }
    });
  }, [nickname, sessionStorage.getItem('address')]);

  // 구매 모달
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => { setIsModalOpen(true) };
  const cancelModal = () => { setIsModalOpen(false) };

  // List 모달
  const [isListModalOpen, setIsListModalOpen] = useState(false);
  const showListModal = () => { setIsListModalOpen(true) };
  const cancelListModal = () => { setIsListModalOpen(false) };


  // 구매 버튼
  function buyButton() {
    setIsModalOpen(false)
  };

  // List 버튼
  const [listPrice, setListPrice] = useState('')
  function listData(listPrice: string) {
    setIsListModalOpen(false)
  };


  return (
    <div className={styles.container}>
      <div className={styles.subText}>Current Price</div>
      {price === -1 ? <div className={styles.price}> -</div> :
        <div className={styles.price}>{price} O₂</div>
      }
      <div style={{ display: 'flex', justifyContent: 'space-around' }}>

        {/* 구매가 가능한 토큰인지? */}
        {activated ?
          <div onClick={showModal} style={{ width: '48%' }}>
            <ButtonDiv disabled={false} text={'Buy now'} icon={'Buy'} />
          </div>
          : <div style={{ width: '48%' }}>
            <ButtonDiv disabled={true} text={'Buy now'} icon={'Buy'} />
          </div>
        }

        {/* NFT를 만든 사람과 접속한 유저가 같은 사람인지 */}
        {userCheck ?
          // 같을때
          <div onClick={showListModal} style={{ width: '48%' }}>
            <ButtonDiv disabled={false} text={'List'} color={'white'} icon={'List'} />
          </div>
          // 다를때
          : <div style={{ width: '48%' }}>
            <ButtonDiv disabled={true} text={'List'} color={'white'} icon={'List'} />
          </div>
        }
      </div>

      <Modal open={isModalOpen} centered
        onCancel={cancelModal}
        footer={null}>
        <div className={styles.modalTitle}>구매하시겠습니까?</div>
        <div onClick={buyButton}><ButtonDiv text={'확인'} /></div>
      </Modal>

      <Modal open={isListModalOpen} centered
        onCancel={cancelListModal}
        footer={null}>
        <div className={styles.modalTitle}>List Price</div>
        <div className={styles.modalINput}>
          <Input id="listPrice" onChange={(e) => setListPrice(e.target.value)} />
          <div className={styles.modalList} onClick={() => listData(listPrice)}><ButtonDiv text={'확인'} /></div>
        </div>
      </Modal>
    </div>
  );
}
