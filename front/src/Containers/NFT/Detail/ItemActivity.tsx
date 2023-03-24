import styles from './ItemActivity.module.scss'
import { Table } from 'antd';
import type { ColumnsType, TableProps } from 'antd/es/table';

interface DataType {
  key: number,
  event: string,
  price: number,
  fromAddress: string,
  fromNickname: string
  toAddress: string,
  toNickname: string,
  date: string,
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
        return <div className={styles.iconDiv}>Sale</div>
      } else if (text === 'Transfer') {
        return <div className={styles.iconDiv}>Transfer</div>;
      } else if (text === 'Minted') {
        return <div className={styles.iconDiv}>Minted</div>;
      } else if (text === 'List') {
        return <div className={styles.iconDiv}>List</div>;
      } else if (text === 'Cancel') {
        return <div className={styles.iconDiv}>Cancel</div>;
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
    render: (price) => <>{price}</>,
    width: '20%',
  },
  {
    title: 'From',
    key: 'fromNickname',
    dataIndex: 'fromNickname',
    // 클릭시 fromAddress 유저 페이지로
    render: (fromNickname) => <>{fromNickname}</>,
    width: '20%',
  },
  {
    title: 'To',
    key: 'toNickname',
    dataIndex: 'toNickname',
    // 클릭시 toAddress 유저 페이지로
    render: (toNickname) => <>{toNickname}</>,
    width: '20%',
  },
  {
    title: 'Date',
    key: 'date',
    dataIndex: 'date',
    render: (date) => <>{date}</>,
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
    date: '2022-02-11',
  },
  {
    key: 2,
    event: 'Sale',
    price: 30.233,
    fromAddress: 'Batbat',
    fromNickname: 'Batbat',
    toAddress: 'GoblinbatKit',
    toNickname: 'GoblinbatKit',
    date: '2022-02-11',
  },
  {
    key: 3,
    event: 'Minted',
    price: 100.23,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblindogKit',
    toNickname: 'GoblindogKit',
    date: '2022-01-01',
  },
  {
    key: 4,
    event: 'List',
    price: 80.0,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblinparrotKit',
    toNickname: 'GoblinparrotKit',
    date: '2022-02-28',
  },
  {
    key: 5,
    event: 'cancel',
    price: 80.0,
    fromAddress: '',
    fromNickname: '',
    toAddress: 'GoblinparrotKit',
    toNickname: 'GoblinparrotKit',
    date: '2022-02-28',
  },
];


export function ItemActivity() {

  return (
    <div className={styles.container}>
      <Table className={styles.table} columns={columns} dataSource={data} pagination={false}
      />
    </div>
  )
}