import styles from './Filter.module.scss';
import { Checkbox, Input } from 'antd';
import type { CheckboxValueType } from 'antd/es/checkbox/Group';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ButtonDiv } from 'component/button/Button';

export function NFTFilter() {
  const Tier = (checkedValues: CheckboxValueType[]) => {
    console.log('Tier = ', checkedValues);
  };
  const Category = (checkedValues: CheckboxValueType[]) => {
    console.log('Category = ', checkedValues);
  };
  const Background = (checkedValues: CheckboxValueType[]) => {
    console.log('Background = ', checkedValues);
  };
  const Eyes = (checkedValues: CheckboxValueType[]) => {
    console.log('Eyes = ', checkedValues);
  };
  const Mouth = (checkedValues: CheckboxValueType[]) => {
    console.log('Mouth = ', checkedValues);
  };
  const Head = (checkedValues: CheckboxValueType[]) => {
    console.log('Head = ', checkedValues);
  };

  const tier = ['1', '2']
  const category = ['Wheat', 'Carrot', 'Corn', 'Cucumber', 'Eggplant', 'Pineapple', 'Potato', 'Radish', 'Strawberry', 'Tomato']
  const background = ['White', 'Red', 'Orange', 'Yellow', 'Green', 'Blue', 'Navy', 'Purple', 'Pink', 'Black']
  const eyes = ['Default', 'Chic', 'Adonis', 'Sleep', 'Smile', 'Sad', 'Mad']
  const mouth = ['Default', 'Smile', 'Yammy', 'Tongue', 'Sad', 'Wow', 'Chu']
  const head = ['Default', 'Hairband', 'Ribbon', 'Headset', 'Nutrients', 'Fork', 'Worm']

  const [visiblePrice, setVisiblePrice] = useState(false);
  const [visibleTier, setVisibleTier] = useState(true);
  const [visibleCategory, setVisibleCategory] = useState(true);
  const [visibleBackground, setVisibleBackground] = useState(true);
  const [visibleEyes, setVisibleEyes] = useState(true);
  const [visibleMouth, setVisibleMouth] = useState(true);
  const [visibleHead, setVisibleHead] = useState(true);

  const [minPrice, setMinPrice] = useState('')
  const [maxPrice, setMaxPrice] = useState('')


  return (
    <div className={styles.container}>
      <div className={styles.Title} onClick={() => setVisiblePrice((prev) => !prev)} >
        <div>Price</div>
        <div>{
          visiblePrice ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visiblePrice ? <></> :
        <>
          <div className={styles.priceDiv}>
            <Input placeholder="Min" onChange={(e) => setMinPrice(e.target.value)} className={styles.inputDiv} type="text" />
            to
            <Input placeholder="Max" onChange={(e) => setMaxPrice(e.target.value)} className={styles.inputDiv} type="text" />
          </div>
          <div>
            <ButtonDiv text={'BTN'} />
          </div>
        </>
      }
      <div className={styles.Title} onClick={() => setVisibleTier((prev) => !prev)} >
        <div>Tier</div>
        <div>{
          visibleTier ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleTier ? <></> :
        <Checkbox.Group
          style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Tier}>
          {tier.map((v) => (
            <div key={v} >
              <Checkbox className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
      <div className={styles.Title} onClick={() => setVisibleCategory((prev) => !prev)}>
        <div>Category</div>
        <div>{
          visibleCategory ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleCategory ? <></> :
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Category}>
          {category.map((v) => (
            <div key={v}>
              <Checkbox className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
      <div className={styles.Title}>Attributes</div>
      <div className={styles.Title} onClick={() => setVisibleBackground((prev) => !prev)}>
        <div>Background</div>
        <div>{
          visibleBackground ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleBackground ? <></> :
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Background}>
          {background.map((v) => (
            <div key={v}>
              <Checkbox key={v} className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
      <div className={styles.Title} onClick={() => setVisibleEyes((prev) => !prev)}>
        <div>Eyes</div>
        <div>{
          visibleEyes ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleEyes ? <></> :
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Eyes}>
          {eyes.map((v) => (
            <div key={v}>
              <Checkbox key={v} className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
      <div className={styles.Title} onClick={() => setVisibleMouth((prev) => !prev)}>
        <div>Mouth</div>
        <div>{
          visibleMouth ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleMouth ? <></> :
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Mouth}>
          {mouth.map((v) => (
            <div key={v}>
              <Checkbox key={v} className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
      <div className={styles.Title} onClick={() => setVisibleHead((prev) => !prev)}>
        <div>Head</div>
        <div>{
          visibleHead ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleHead ? <></> :
        <Checkbox.Group style={{ width: '100%', display: 'flex', flexDirection: 'column' }}
          onChange={Head}>
          {head.map((v) => (
            <div key={v}>
              <Checkbox key={v} className={styles.filterText} value={v} >{v}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
    </div >
  );

}