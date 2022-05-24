const GlobalModel = {
  namespace: 'global',
  state: {
    collapsed: false,
    notices: [],
    pageSize: 15,
  },
  effects: {
  },
  reducers: {
    changeLayoutCollapsed(
      state = {
        notices: [],
        collapsed: true,
      },
      { payload },
    ) {
      return { ...state, collapsed: payload };
    },
    success(state, { payload }) {
      return { ...state, ...payload };
    },
  },
  subscriptions: {
    setup({ history, dispatch }) {
      // Subscribe history(url) change, trigger `load` action if pathname is `/`
      history.listen(({ pathname, search }) => {
        if (typeof window.ga !== 'undefined') {
          window.ga('send', 'pageview', pathname + search);
        }
        const h = window.innerHeight;
        let pageSize = 10;
        if (h < 617) { // 小于7行数据 309 + 44 * 7
          pageSize = 5;
        } else if (h > 837) { // 大于12行数据 309 + 44 * 12
          pageSize = 15;
        }
        dispatch({ type: 'success', payload: { pageSize } });
      });
    },
  },
};
export default GlobalModel;
