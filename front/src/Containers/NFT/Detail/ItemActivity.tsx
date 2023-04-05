import { useEffect, useState } from 'react';

import styles from './ItemActivity.module.scss'
import { Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';

import List from 'assets/nftDetail/chart/list.png'
import Sale from 'assets/nftDetail/chart/sale.png'
import Item from 'assets/nftDetail/chart/item.png'
import Cancel from 'assets/nftDetail/chart/cancel.png'
import Minted from 'assets/nftDetail/chart/minted.png'
import Transfer from 'assets/nftDetail/chart/transfer.png'
import { Link } from 'react-router-dom';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';

interface DataType {
  key: number,
  event: string,
  price: number,
  fromAddress: string,
  fromNickname: string
  toAddress: string,
  toNickname: string,
  date: any,
}


export function getTimeDiff(dateString: string): string {
  const now = new Date();
  const date = new Date(dateString);
  const diffInMs = now.getTime() - date.getTime();

  // 초 단위로 출력
  const diffInSeconds = Math.floor(diffInMs / 1000);
  if (diffInSeconds < 60) {
    return `${diffInSeconds}초 전`;
  }

  // 분 단위로 출력
  const diffInMinutes = Math.floor(diffInMs / (1000 * 60));
  if (diffInMinutes < 60) {
    return `${diffInMinutes}분 전`;
  }
  // 분 단위로 출력
  const diffInHours = Math.floor(diffInMs / (1000 * 60 * 60));
  if (diffInHours < 24) {
    return `${diffInHours}시간 전`;
  }
  // 일 단위로 출력
  const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24));
  return `${diffInDays}일 전`;
}

const columns: ColumnsType<DataType> = [
  {
    title: 'Event',
    dataIndex: 'event',
    key: 'event',
    render: (text) => {
      if (!text) {
        text = '-';
      } else if (text === 'Sales') {
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
        text: 'Sales',
        value: 'Sales',
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


export function ItemActivity() {
  const url = window.location.href.split("/");
  const nftAddress: string = url[url.length - 1]
  const [data, setData] = useState<DataType[]>([]);


  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/activity/${nftAddress}`,
    }).then((res) => {
      setData(res.data);
    });
  }, [nftAddress]);
  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <img className={styles.icon} src={Item} alt="" />
        Item Activity</div>
      <Table rowKey={(row) => row.key} className={styles.table} columns={columns} dataSource={data} pagination={false} showSorterTooltip={false}
      />
    </div>
  )
}