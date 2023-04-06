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
  const [sort, setSort] = useState('');

  const onChange = (value: string) => {
    setSort(value)
  };

  const url = window.location.href.split("/");
  const cropType: string = url[url.length - 1]

  const [cropData, setCropData] = useState<CategoryInfoType>();
  const [tier, setTier] = useState([]);
  const [categoryType, setCategoryType] = useState([]);
  const [bg, setbg] = useState([]);
  const [eyes, setEyes] = useState([]);
  const [mouth, setMouth] = useState([]);
  const [headGear, setHeadGear] = useState([]);
  const [min, setMin] = useState<string>();
  const [max, setMax] = useState<string>();

  const changeMin = (value: any) => { setMin(value) };
  const changeMax = (value: any) => { setMax(value) };
  const changeTier = (value: any) => { setTier(value) };
  const changeCategory = (value: any) => { setCategoryType(value) };
  const changeBackground = (value: any) => { setbg(value) };
  const changeEyes = (value: any) => { setEyes(value) };
  const changeMouth = (value: any) => { setMouth(value) };
  const changeHead = (value: any) => { setHeadGear(value) };


  useEffect(() => {
    axios({
      method: 'post',
      url: baseURL + `/nft/list/${cropType}`,
      data: {
        min: min,
        max: max,
        tier: tier,
        cropType: categoryType,
        bg: bg,
        eyes: eyes,
        mouth: mouth,
        headGear: headGear,
        sort: sort,
      },
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
        "Content-Type": "application/json"
      },
    }).then((res) => {
      console.log(res.data);
      setCropData(res.data);
    });
  }, [min, max, tier, categoryType, cropType, bg, eyes, mouth, headGear, sort]);


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
        <NFTCategoryFilter changeMax={changeMax} changeMin={changeMin} changeTier={changeTier} changeCategory={changeCategory} changeBackground={changeBackground} changeEyes={changeEyes} changeMouth={changeMouth} changeHead={changeHead} />
        <NFTCategoryCard cropData={cropData} />
      </div>
    </div>
  );
}