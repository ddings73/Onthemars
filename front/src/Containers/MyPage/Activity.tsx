import { Table } from 'antd';
import { ColumnsType } from 'antd/es/table';
import { getTimeDiff } from 'Containers/NFT/Detail/ItemActivity';
import React, { useEffect, useState } from 'react';
import styles from './Activity.module.scss';

import List from 'assets/nftDetail/chart/list.png'
import Sale from 'assets/nftDetail/chart/sale.png'
import Item from 'assets/nftDetail/chart/item.png'
import Cancel from 'assets/nftDetail/chart/cancel.png'
import Minted from 'assets/nftDetail/chart/minted.png'
import Transfer from 'assets/nftDetail/chart/transfer.png'
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';

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
    title: 'Event',
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
    filters: [
      {
        text: 'Sale',
        value: 'Sale',
      },
      {
        text: 'Transfer',
        value: 'Transfer',
      },
      {
        text: 'Minted',
        value: 'Minted',
      },
      {
        text: 'List',
        value: 'List',
      },
      {
        text: 'Cancel',
        value: 'Cancel',
      },
    ],
    filterMode: 'tree',
    filterSearch: true,
    // @ts-ignore
    onFilter: (value: string, record) => record.event.startsWith(value),
    width: '20%',
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
    width: '20%',
  },
  {
    title: 'From',
    dataIndex: 'fromNickname',
    key: 'fromNickname',
    // 클릭시 fromAddress 유저 페이지로
    render: (fromNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.fromAddress}`}>{fromNickname}</Link>,
    width: '20%',
  },
  {
    title: 'To',
    dataIndex: 'toNickname',
    key: 'toNickname',
    // 클릭시 toAddress 유저 페이지로
    render: (toNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.toAddress}`}>{toNickname}</Link>,
    width: '20%',
  },
  {
    title: 'Date',
    dataIndex: 'date',
    key: 'date',
    render: (date) => <>{getTimeDiff(date)}</>,
    width: '20%',
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
  }, [address]);


  return (
    <div className={styles.container}>
      <Table rowKey={(row) => row.transactionId} className={styles.table} columns={columns} dataSource={data} pagination={false} showSorterTooltip={false}
      />
    </div>
  );
}

export default Activity;
