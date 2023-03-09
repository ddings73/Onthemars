import React from "react";
import "./SearchBox.css";
import SearchIcon from "@mui/icons-material/Search";

export function SearchBox() {
  return (
    <div className="searchBox">
      <input className="searchInput" type="text" name="" placeholder="Search" />
      <button className="searchButton">
        <SearchIcon />
      </button>
    </div>
  );
}

export default SearchBox;
