import styles from './index.module.scss';
import { Select } from 'antd';
import { NFTCategoryCard } from './CardDiv';
import { NFTCategoryFilter } from './Filter';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';
import { CategoryInfoType } from 'Store/type/CategoryInfo';
import { Categoryinfo } from './Info';

export function CategorySearch() {
  const onChange = (value: string) => {
    // console.log(`selected ${value}`);
  };
  const url = window.location.href.split("/");
  const cropType: string = url[url.length - 1]
  const [data, setData] = useState<CategoryInfoType>();

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/list/${cropType}`,
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    }).then((res) => {
      console.log(res.data);
      setData(res.data);
    });
  }, [cropType]);


  return (
    <div className={styles.container}>
      <Categoryinfo props={cropType} />
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
        <NFTCategoryFilter />
        <NFTCategoryCard />
      </div>
    </div>
  );
}