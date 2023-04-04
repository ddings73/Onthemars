import styles from './index.module.scss';
import { Select } from 'antd';
import { NFTFilter } from './Filter';
import { NFTCard } from './CardDiv';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';
import { CheckboxValueType } from 'antd/es/checkbox/Group';

export function NFTSearch() {
  const [sort, setSort] = useState('');
  const onChangeSort = (value: string) => {
    setSort(value)
  };
  const url = window.location.href.split("/");
  const keyword: string = url[url.length - 1]

  const [searchData, setSearchData] = useState();
  const [tier, setTier] = useState([]);
  const [cropType, setCropType] = useState([]);
  const [bg, setbg] = useState([]);
  const [eyes, setEyes] = useState([]);
  const [mouth, setMouth] = useState([]);
  const [headGear, setHeadGear] = useState([]);
  const [min, setMin] = useState();
  const [max, setMax] = useState();


  const changeMin = (value: any) => { setMin(value) };
  const changeMax = (value: any) => { setMax(value) };
  const changeTier = (value: any) => { setTier(value) };
  const changeCategory = (value: any) => { setCropType(value) };
  const changeBackground = (value: any) => { setbg(value) };
  const changeEyes = (value: any) => { setEyes(value) };
  const changeMouth = (value: any) => { setMouth(value) };
  const changeHead = (value: any) => { setHeadGear(value) };


  useEffect(() => {

    axios({
      method: 'post',
      url: baseURL + `/nft/search`,
      data: {
        keyword: decodeURIComponent(keyword),
        minPrice: min,
        maxPrice: max,
        sort: sort,
        tier: tier,
        cropType: cropType,
        bg: bg,
        eyes: eyes,
        mouth: mouth,
        headGear: headGear,
      },
      headers: {
        "Content-Type": "application/json"
      }
    }).then((res) => {
      console.log(res.data);
      setSearchData(res.data);
    });
  }, [keyword, min, max, tier, cropType, bg, eyes, mouth, headGear, sort]);

  return (
    <div className={styles.container}>
      <Select
        className={styles.select}
        size={'large'}
        placeholder="Sort by"
        optionFilterProp="children"
        onChange={onChangeSort}
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
        <NFTFilter changeMax={changeMax} changeMin={changeMin} changeTier={changeTier} changeCategory={changeCategory} changeBackground={changeBackground} changeEyes={changeEyes} changeMouth={changeMouth} changeHead={changeHead} />
        <NFTCard searchData={searchData} />
      </div>
    </div>
  );
}