import styles from './index.module.scss';
import { Select } from 'antd';
import { NFTFilter } from './Filter';
import { NFTCard } from './CardDiv';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';

export function NFTSearch() {
  const onChange = (value: string) => {
    console.log(`selected ${value}`);
  };
  const url = window.location.href.split("/");
  const keyword: string = url[url.length - 1]
  // 검색 수정중
  const [searchData, setSearchData] = useState();

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/search`,
      data: { keyword: keyword }
    }).then((res) => {
      console.log(res.data);
      setSearchData(res.data);
    });
  }, []);


  return (
    <div className={styles.container}>
      <Select
        className={styles.select}
        size={'large'}
        placeholder="Sort by"
        optionFilterProp="children"
        onChange={onChange}
        filterOption={(input, option) =>
          (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
        }
        options={[
          {
            value: '1',
            label: 'Price low to high',
          },
          {
            value: '2',
            label: 'Price high to low',
          },
          {
            value: '3',
            label: 'Recently Listed',
          },
        ]}
      />
      <div className={styles.filterDiv}>
        <NFTFilter />
        <NFTCard />
      </div>
    </div>
  );
}