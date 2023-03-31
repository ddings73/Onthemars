import React from 'react';
import { Select } from 'antd';

function SortBy({ setValue, value }: any) {
  return (
    <>
      <Select
        size={'large'}
        style={{ width: '20vw' }}
        listHeight={150}
        listItemHeight={1}
        placeholder='Select Crop'
        allowClear
        onChange={(value: string) => {
          setValue(value);
        }}
        options={[
          {
            value: 'CRS01',
            label: 'Carrot',
          },
          {
            value: 'CRS02',
            label: 'Corn',
          },
          {
            value: 'CRS03',
            label: 'Cucumber',
          },
          {
            value: 'CRS04',
            label: 'Eggplant',
          },
          {
            value: 'CRS05',
            label: 'Pineapple',
          },
          {
            value: 'CRS06',
            label: 'Potato',
          },
          {
            value: 'CRS07',
            label: 'Radish',
          },
          {
            value: 'CRS08',
            label: 'Strawberry',
          },
          {
            value: 'CRS09',
            label: 'Tomato',
          },
          {
            value: 'CRS10',
            label: 'Wheat',
          },
        ]}
      />
    </>
  );
}

export default SortBy;
