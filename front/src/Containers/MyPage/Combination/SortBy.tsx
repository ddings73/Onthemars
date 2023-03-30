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
        defaultValue="All"
        allowClear
        onChange={(value: string) => {
          setValue(value);
        }}
        options={[
          {
            value: 'Carrot',
            label: 'Carrot',
          },
          {
            value: 'Corn',
            label: 'Corn',
          },
          {
            value: 'Cucumber',
            label: 'Cucumber',
          },
          {
            value: 'Eggplant',
            label: 'Eggplant',
          },
          {
            value: 'Pineapple',
            label: 'Pineapple',
          },
          {
            value: 'Potato',
            label: 'Potato',
          },
          {
            value: 'Radish',
            label: 'Radish',
          },
          {
            value: 'Strawberry',
            label: 'Strawberry',
          },
          {
            value: 'Tomato',
            label: 'Tomato',
          },
          {
            value: 'Wheat',
            label: 'Wheat',
          },
        ]}
      />
    </>
  );
}

export default SortBy;
