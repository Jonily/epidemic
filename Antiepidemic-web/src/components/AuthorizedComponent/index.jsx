import { useModel } from 'umi';

const AuthorizedComponent = ({ children, ...props }) => {
    // const { initialState, setInitialState } = useModel('@@initialState');
    // const { privileges = [] } = initialState;
    // const { code } = props;
    // const p = privileges.filter(item => item.code === code)[0] || undefined;
    // return p ? children : null
    return children
};

export default AuthorizedComponent;

