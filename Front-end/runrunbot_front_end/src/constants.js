// 统一 TOKEN_KEY 命名，后面 logout/login 都会用到
export const TOKEN_KEY = "token";

// // 后端 API 根路径
// export const API_ROOT = "https://XXX.uw.r.appspot.com";
// export const API_ROOT = "http://localhost:5000"; //用于开发时测试
export const API_ROOT = "http://localhost:8080"; //和后端统一使用8080端口
// 搜索功能可继续保留
export const SEARCH_KEY = {
  all: 0,
  keyword: 1,
  user: 2,
};
