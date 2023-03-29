import React from 'react';
import { Select } from 'antd';

function SortBy() {
  const onChange = (value: string) => {
    console.log(`selected ${value}`);
  };
  return (
    <>
      <Select
        size={'large'}
        style={{width:'20vw'}}
        listHeight={150}
        listItemHeight={1}
        placeholder="Sort by"
        optionFilterProp="children"
        onChange={onChange}
        filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
        options={[
          {
            value: '0',
            label: 'All',
          },
          {
            value: '1',
            label: 'Carrot',
          },
          {
            value: '2',
            label: 'Corn',
          },
          {
            value: '3',
            label: 'Cucumber',
          },
          {
            value: '4',
            label: 'Eggplant',
          },
          {
            value: '5',
            label: 'Pineapple',
          },
          {
            value: '6',
            label: 'Potato',
          },
          {
            value: '7',
            label: 'Radish',
          },
          {
            value: '8',
            label: 'Strawberry',
          },
          {
            value: '9',
            label: 'Tomato',
          },
          {
            value: '10',
            label: 'Wheat',
          },
        ]}
      />
    </>
  );
}

export default SortBy;
