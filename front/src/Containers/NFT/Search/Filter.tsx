import styles from './Filter.module.scss';
import { Checkbox, Input } from 'antd';
import type { CheckboxValueType } from 'antd/es/checkbox/Group';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ButtonDiv } from 'component/button/Button';

export function NFTFilter(props: any) {

  const Tier = (checkedValues: CheckboxValueType[]) => {
    props.changeTier(checkedValues)
  };
  const Category = (checkedValues: CheckboxValueType[]) => {
    props.changeCategory(checkedValues)
  };
  const Background = (checkedValues: CheckboxValueType[]) => {
    props.changeBackground(checkedValues)
  };
  const Eyes = (checkedValues: CheckboxValueType[]) => {
    props.changeEyes(checkedValues)
  };
  const Mouth = (checkedValues: CheckboxValueType[]) => {
    props.changeMouth(checkedValues)
  };
  const Head = (checkedValues: CheckboxValueType[]) => {
    props.changeHead(checkedValues)
  };

  const tier = ['1', '2']
  const category = { 'CRS10': 'Wheat', 'CRS01': 'Carrot', 'CRS02': 'Corn', 'CRS03': 'Cucumber', 'CRS04': 'Eggplant', 'CRS05': 'Pineapple', 'CRS06': 'Potato', 'CRS07': 'Radish', 'CRS08': 'Strawberry', 'CRS09': 'Tomato' }
  const background = { 'CLR01': 'White', 'CLR02': 'Red', 'CLR03': 'Orange', 'CLR04': 'Yellow', 'CLR05': 'Green', 'CLR06': 'Blue', 'CLR07': 'Navy', 'CLR08': 'Purple', 'CLR09': 'Pink', 'CLR10': 'Brown' }
  const eyes = { 'EYE01': 'Default', 'EYE02': 'Chic', 'EYE03': 'Adonis', 'EYE04': 'Sleep', 'EYE05': 'Smile', 'EYE06': 'Sad', 'EYE07': 'Mad' }
  const mouth = { 'MOU01': 'Default', 'MOU02': 'Smile', 'MOU03': 'Mustache', 'MOU04': 'Tongue', 'MOU05': 'Sad', 'MOU06': 'Wow', 'MOU07': 'Chu' }
  const head = { 'HDG01': 'Default', 'HDG02': 'Hairband', 'HDG03': 'Ribbon', 'HDG04': 'Headset', 'HDG05': 'Nutrients', 'HDG06': 'Fork', 'HDG07': 'Worm' }

  const [visiblePrice, setVisiblePrice] = useState(false);
  const [visibleTier, setVisibleTier] = useState(true);
  const [visibleCategory, setVisibleCategory] = useState(true);
  const [visibleBackground, setVisibleBackground] = useState(true);
  const [visibleEyes, setVisibleEyes] = useState(true);
  const [visibleMouth, setVisibleMouth] = useState(true);
  const [visibleHead, setVisibleHead] = useState(true);

  const [min, setMin] = useState<string>();
  const [max, setMax] = useState<string>();

  const sendPrice = () => {
    props.changeMin(min)
    props.changeMax(max)
  }
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
            <Input placeholder="Min" onChange={(e) => setMin(e.target.value)} className={styles.inputDiv} type="text" />
            to
            <Input placeholder="Max" onChange={(e) => setMax(e.target.value)} className={styles.inputDiv} type="text" />
          </div>
          <div onClick={sendPrice}>
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
          {Object.entries(category).map(([key, value]) => (
            <div key={key}>
              <Checkbox className={styles.filterText} value={key} >{value}</Checkbox>
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
          {Object.entries(background).map(([key, velue]) => (
            <div key={key}>
              <Checkbox key={key} className={styles.filterText} value={key} >{velue}</Checkbox>
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
          {Object.entries(eyes).map(([key, velue]) => (
            <div key={key}>
              <Checkbox key={key} className={styles.filterText} value={key} >{velue}</Checkbox>
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
          {Object.entries(mouth).map(([key, velue]) => (
            <div key={key}>
              <Checkbox key={key} className={styles.filterText} value={key} >{velue}</Checkbox>
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
          {Object.entries(head).map(([key, velue]) => (
            <div key={key}>
              <Checkbox key={key} className={styles.filterText} value={key} >{velue}</Checkbox>
            </div>
          )
          )}
        </Checkbox.Group>
      }
    </div >
  );

}