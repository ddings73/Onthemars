import React, { useState } from 'react';
import './SearchBox.css';
import SearchIcon from '@mui/icons-material/Search';
import { Input } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

export function SearchBox() {
  const navigate = useNavigate();

  // input의 value
  const [value, setValue] = useState<string>();

  // 검색
  function searchEvent() {
    if (value && value.length > 0) {
      const searchValue = value
      if (searchValue && searchValue.length > 0) {
        navigate(`/nft/${value}`)
      }
    }
  }


  return (
    <div className="searchBox">
      <input className="searchInput"
        type="text"
        name=""
        value={value}
        placeholder="Search"
        onChange={(e) => {
          setValue(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            searchEvent();
            setValue('')
          }
        }}
      />
      <button className="searchButton" onClick={searchEvent}>
        <SearchIcon />
      </button>
    </div>
  );
}

export default SearchBox;
