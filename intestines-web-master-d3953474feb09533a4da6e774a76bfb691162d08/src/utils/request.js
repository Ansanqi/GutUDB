import axios from "axios"
// import { showLoadingToast, closeToast } from "vant"

const defaultConfig = {
	baseURL: window.config.baseUrl,
	// baseURL: '/api',
	timeout: window.config.timeout,
}

const customAxios = axios.create(defaultConfig)

function request (method = "GET", url, data, config) {
	const conf = {
		method: method == 'down' ? 'GET' : method,
		url,
		headers: {}
	}
	if (data) {
		if (method == "GET") {
			conf.params = data
		} else if (method == "POST") {
			conf.data = data
		}
	}
	if (method == 'down') {
		conf.responseType = 'blob'
		conf.params = data
		conf.timeout = 0
	}
	if (config?.headers) {
		conf.headers = config.headers
	}

	if (localStorage.token) {
		conf.headers.Authorization = localStorage.token
	}

	// if (config?.showToast == undefined || config.showToast === true) {
	// 	showLoadingToast({
	// 		message: "加载中...",
	// 		forbidClick: true,
	// 		duration: 0
	// 	})
	// }

	return customAxios
		.request(conf)
		.then((response) => {
			if (method == 'down') {
				if (typeof response.data === "object" && response.data.type == "application/json") {
					const blob = new Blob([response.data], { type: response.data.type });
					const reader = new FileReader();
					reader.onload = function(event) {
						const content = event.target.result;
						try {
							const jsonObject = JSON.parse(content);
							ElMessage({
								showClose: true,
								message: jsonObject.message,
								type: 'error',
							})
						} catch (error) {
							console.error("解析 JSON 失败:", error);
						}
					}
					reader.readAsText(blob);
					return
				}
				try {
					let fileName = '';
					if (config) {
						fileName = config
					} else {
						const currentDate = new Date();
						const formattedDate = currentDate.toISOString().slice(0, 19).replace(/:/g, '-').replace('T', '_');
						fileName = formattedDate
					}

					const contentType = response.headers['content-type']; // 从服务器响应中获取 Content-Type
					const fileExtension = contentType.split('/').pop();

					if (response.headers['content-type'].includes('text/tab-separated-values')) {
						fileName += '.tsv'
					}else if(response.headers['content-type'].includes('text/plain')){
						fileName += '.txt'
					}else if(response.headers['content-type'].includes('text/csv')){
						fileName += '.csv'
					}

					// let arr = []
					// if (response.headers['content-type'] == 'text/plain') {
						// const contentArray = new TextEncoder().encode(response.data);
						// arr = [contentArray]
					// }else{
					// 	arr = [response.data]
					// }
					const blob = new Blob([response.data], { type: response.headers['content-type'] });
					const downloadLink = document.createElement('a');
					downloadLink.href = URL.createObjectURL(blob);
					downloadLink.download = fileName;
					downloadLink.click();
					URL.revokeObjectURL(downloadLink.href);
				} catch (error) {
					console.error('下载文件时出错：', error);
				}
			} else {
				if (response.data.code != 0) {
					ElMessage({
						showClose: true,
						message: response.data.message,
						type: 'error',
					})
					// throw new Error(response.data.message)
					return
				}
				// closeToast()
				return response.data
			}
		})
		.catch((error) => {
			ElMessage({
				showClose: true,
				message: error.message,
				type: 'error',
			})
			throw error
		})
}

function objectToQueryString(obj) {
  const queryParams = [];
  for (const key in obj) {
    if (obj.hasOwnProperty(key)) {
      queryParams.push(encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]));
    }
  }
  return queryParams.join('&');
}

export const api = {
	request,
	/**
	 *
	 * @param {string} url 请求地址可以全地址 也可以去除baseURL后的地址
	 * @param {object} params 参数
	 * @param {object} config { showToast:Boolean 是否显示遮罩 默认true , showError:Boolean 是否自动显示错误信息 默认true ,
	 * headers : {
	 * 	"Content-Type": "multipart/form-data",
			"Accept": "application/json",
			"Cache-Control": "no-cache",
			"Accept-Language": "zh-CN,zh;" } Cookie和Referer无法修改
	 * @returns
	 */
	get: (url, params, config) => {
		return request("GET", url, params, config)
	},
	post: (url, data, config) => {
		return request("POST", url, data, config)
	},
	down: (url, params, name) => {
		return new Promise((resolve, reject)=>{
			const obj = {}
			for (const key in params) {
				const value = params[key];
				if (value) {
					obj[key] = value
				}
			}
			const paramsF = objectToQueryString(obj)

			const href = `${window.config.baseUrl}${url}?${paramsF}`

			// window.open(href)
			console.log(href);
			window.open(href, '_blank', 'noopener');

			resolve()
		})
		// return request("down", url, params, name)
	},
}
