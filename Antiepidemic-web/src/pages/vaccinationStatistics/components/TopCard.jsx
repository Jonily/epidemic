import { Col, Row } from 'antd';
import styles from './index.less';

const topColResponsiveProps = {
  xs: 24,
  sm: 24,
  md: 12,
  lg: 12,
  // xl:4,
  flex: 1,
  /* style: {
    marginBottom: 12,
  }, */
};
//顶部四个卡片
const TopCard = ({ oneVal = '',oneTitle = '总次数',twoVal = '',twoTitle = '接种完成数',threeVal='',threeTitle = '今日新增次数',fourVal='',fourTitle = '今日新增完成' }) => {
  return (
    <Row gutter={12} type="flex" style={{ marginTop: 10 }}>
      <Col {...topColResponsiveProps}>
        <div
          className={styles.total2}
        >
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                // marginLeft: 15,
              }}
            >
              <p style={{ marginBottom: 5, fontSize: 34, fontWeight: 600, color: '#fff' }}>
                {(oneVal + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}
                {/* .replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')   每千分位一个逗号 */}
                {/* <span style={{ fontSize: 14, fontWeight: 400 }}>{enterSum?.u}</span> */}
              </p>
              <p style={{ margin: 0, color: '#fff' }}>{oneTitle}</p>
            </div>
          </div>
        </div>
      </Col>
      <Col {...topColResponsiveProps}>
        <div
          className={styles.total4}
        >
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                // marginLeft: 15,
              }}
            >
              <p style={{ marginBottom: 5, fontSize: 34, fontWeight: 600, color: '#fff' }}>
                {(twoVal + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}
                {/* .replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')   每千分位一个逗号 */}
                {/* <span style={{ fontSize: 14, fontWeight: 400 }}>{grainIndexAdd?.u}</span> */}
              </p>
              <p style={{ margin: 0, color: '#fff' }}>{twoTitle}</p>
            </div>
          </div>
        </div>
      </Col>
      <Col {...topColResponsiveProps}>
        <div
          className={styles.total5}
        >
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                // marginLeft: 15,
              }}
            >
              <p style={{ marginBottom: 5, fontSize: 34, fontWeight: 600, color: '#fff' }}>
                {(threeVal + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}
                {/* .replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')   每千分位一个逗号 */}
                {/* <span style={{ fontSize: 14, fontWeight: 400 }}>{grainIndex?.u}</span> */}
              </p>
              <p style={{ margin: 0, color: '#fff' }}>{threeTitle}</p>
            </div>
          </div>
        </div>
      </Col>
      <Col {...topColResponsiveProps}>
        <div
          className={styles.total1}
        >
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                // marginLeft: 15,
              }}
            >
              <p style={{ marginBottom: 5, fontSize: 34, fontWeight: 600, color: '#fff' }}>
                {(fourVal + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}
                {/* .replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')   每千分位一个逗号 */}
                {/* <span style={{ fontSize: 14, fontWeight: 400 }}>{reserveSum?.u}</span> */}
              </p>
              <p style={{ margin: 0, color: '#fff' }}>{fourTitle}</p>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  );
};
export default TopCard;
