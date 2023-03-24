import styles from './index.module.scss';
import { Select } from 'antd';
import { NFTFilter } from './Filter';
import { NFTCard } from './CardDiv';

export function NFTSearch() {
  const onChange = (value: string) => {
    console.log(`selected ${value}`);
  };



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