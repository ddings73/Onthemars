import { Checkbox, Table } from 'antd';
import { ColumnsType } from 'antd/es/table';
import { getTimeDiff } from 'Containers/NFT/Detail/ItemActivity';
import { useEffect, useState } from 'react';
import styles from './Activity.module.scss';

import Check from 'assets/nftDetail/check.png'

import List from 'assets/nftDetail/chart/list.png'
import Sale from 'assets/nftDetail/chart/sale.png'
import Cancel from 'assets/nftDetail/chart/cancel.png'
import Minted from 'assets/nftDetail/chart/minted.png'
import Transfer from 'assets/nftDetail/chart/transfer.png'
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import { baseURL, imgBaseURL } from 'apis/baseApi';
import { CheckboxValueType } from 'antd/es/checkbox/Group';

interface DataType {
  transactionId: number,
  imgUrl: string,
  cropType: string,
  nftName: string,
  event: string,
  price: number,
  fromAddress: string,
  fromNickname: string
  toAddress: string,
  toNickname: string,
  date: any,
}


const columns: ColumnsType<DataType> = [
  {
    title: 'EVENT',
    dataIndex: 'event',
    key: 'event',
    render: (text) => {
      if (!text) {
        text = '-';
      } else if (text === 'Sale') {
        return <div className={styles.iconDiv}>
          <img className={styles.icon} src={Sale} alt="" />
          Sale</div>
      } else if (text === 'Transfer') {
        return <div className={styles.iconDiv}>
          <img className={styles.icon} src={Transfer} alt="" />
          Transfer</div>;
      } else if (text === 'Minted') {
        return <div className={styles.iconDiv}>
          <img className={styles.icon} src={Minted} alt="" />
          Minted</div>;
      } else if (text === 'List') {
        return <div className={styles.iconDiv}>
          <img className={styles.icon} src={List} alt="" />
          List</div>;
      } else if (text === 'Cancel') {
        return <div className={styles.iconDiv}>
          <img className={styles.icon} src={Cancel} alt="" />
          Cancel</div>;
      }
    },
    filterMode: 'tree',
    filterSearch: true,
    // @ts-ignore
    onFilter: (value: string, record) => record.event.startsWith(value),
    width: '15%',
  },
  {
    title: 'ITEM',
    dataIndex: 'cropParent',
    key: 'cropParent',
    render: (cropParent, row) =>
      <div className={styles.itemDiv}>
        <img className={styles.cropImg} src={imgBaseURL + row.imgUrl} alt="" />
        <div>
          <div style={{ display: 'flex' }}>{cropParent}<img className={styles.icon} src={Check} alt="" /></div><div style={{ fontWeight: '700' }}>{row.nftName}</div></div> </div>,
    width: '25%',
  },
  {
    title: 'Price',
    dataIndex: 'price',
    key: 'price',
    render: (price) => {
      if (price === -1.0) {
        return '';
      }
      return <>{price}</>;
    },
    sorter: (a, b) => a.price - b.price,
    width: '15%',
  },
  {
    title: 'From',
    dataIndex: 'fromNickname',
    key: 'fromNickname',
    // 클릭시 fromAddress 유저 페이지로
    render: (fromNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.fromAddress}`}>{fromNickname}</Link>,
    width: '15%',
  },
  {
    title: 'To',
    dataIndex: 'toNickname',
    key: 'toNickname',
    // 클릭시 toAddress 유저 페이지로
    render: (toNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.toAddress}`}>{toNickname}</Link>,
    width: '15%',
  },
  {
    title: 'Date',
    dataIndex: 'date',
    key: 'date',
    render: (date) => <>{getTimeDiff(date)}</>,
    width: '15%',
  },
];



function Activity() {

  const [data, setData] = useState<DataType[]>([]);
  const address = useParams().address;

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/${address}/activity`,
    }).then((res) => {
      setData(res.data);
      console.log('123', res.data);

    });
  }, []);
  const Event = (checkedValues: CheckboxValueType[]) => {
    console.log('Event = ', checkedValues);
  };
  const event = ['Minted', 'List', 'Sales', 'Transfer', 'Cancel']

  return (
    <div className={styles.container}>
      <div className={styles.eventDiv}>
        <div className={styles.eventTitle}>Event Type</div>
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Event}>
          {event.map((v) => (
            <div key={v}>
              <Checkbox key={v} className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      </div>
      <Table rowKey={(row) => row.transactionId} className={styles.table} columns={columns} dataSource={data} pagination={false} showSorterTooltip={false}
      />
    </div>
  );
}

export default Activity;
