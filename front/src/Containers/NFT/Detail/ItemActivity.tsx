import styles from './ItemActivity.module.scss'
import { Table } from 'antd';
import type { ColumnsType, TableProps } from 'antd/es/table';
import List from 'assets/nftDetail/chart/list.png'
import Sale from 'assets/nftDetail/chart/sale.png'
import Item from 'assets/nftDetail/chart/item.png'
import Cancel from 'assets/nftDetail/chart/cancel.png'
import Minted from 'assets/nftDetail/chart/minted.png'
import Transfer from 'assets/nftDetail/chart/transfer.png'
import { Link } from 'react-router-dom';

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

function getTimeDiff(dateString: string): string {
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
    key: 'price',
    dataIndex: 'price',
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
    key: 'fromNickname',
    dataIndex: 'fromNickname',
    // 클릭시 fromAddress 유저 페이지로
    render: (fromNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.fromAddress}`}>{fromNickname}</Link>,
    width: '20%',
  },
  {
    title: 'To',
    key: 'toNickname',
    dataIndex: 'toNickname',
    // 클릭시 toAddress 유저 페이지로
    render: (toNickname, row) => <Link className={styles.colorLink} to={`/mypage/${row.toAddress}`}>{toNickname}</Link>,
    width: '20%',
  },
  {
    title: 'Date',
    key: 'date',
    dataIndex: 'date',
    render: (date) => <>{getTimeDiff(date)}</>,
    width: '20%',
  },
];

const data: DataType[] = [
  {
    key: 1,
    event: 'Transfer',
    price: 30.233,
    fromAddress: 'Batbat',
    fromNickname: 'Batbat',
    toAddress: 'GoblinbatKit',
    toNickname: 'GoblinbatKit',
    date: '2023-03-27T10:48:41',
  },
  {
    key: 2,
    event: 'Sale',
    price: 30.233,
    fromAddress: 'Batbat',
    fromNickname: 'Batbat',
    toAddress: 'GoblinbatKit',
    toNickname: 'GoblinbatKit',
    date: '2023-03-26T10:48:41',
  },
  {
    key: 3,
    event: 'Minted',
    price: -1.0,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblindogKit',
    toNickname: 'GoblindogKit',
    date: '2023-03-25T10:48:41',
  },
  {
    key: 4,
    event: 'List',
    price: 80.0,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblinparrotKit',
    toNickname: 'GoblinparrotKit',
    date: '2023-03-24T10:48:41',
  },
  {
    key: 5,
    event: 'Cancel',
    price: 80.0,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblinparrotKit',
    toNickname: 'GoblinparrotKit',
    date: '2023-03-23T10:48:41',
  },
];


export function ItemActivity() {

  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <img className={styles.icon} src={Item} alt="" />
        Item Actibity</div>
      <Table className={styles.table} columns={columns} dataSource={data} pagination={false} showSorterTooltip={false}
      />
    </div>
  )
}