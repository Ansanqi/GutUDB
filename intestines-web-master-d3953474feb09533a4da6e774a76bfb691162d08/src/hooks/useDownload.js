import { ElNotification } from "element-plus"

/**
 * @description 接收数据流生成 blob，创建链接，下载文件
 * @param {String} imageUrl 地址 (必传)
 * @param {Boolean} isNotify 是否有导出消息提示 (默认为 true)
 * */
export const useDownload = async (imageUrl, isNotify = true) => {
	// if (isNotify) {
	// 	ElNotification({
	// 		title: "温馨提示",
	// 		message: "开始下载，请您耐心等待！",
	// 		type: "info",
	// 		duration: 3000,
	// 	})
	// }
	try {
		const res = await fetch(imageUrl).then((response) => response.blob()) // 转换为 Blob 对象
		const imgArr = imageUrl.split("/").slice(-1)[0].split(".")
		console.log(imgArr)
		const tempName = imgArr[0]
		const fileType = "." + imgArr[1]
		const blob = new Blob([res])
		// 兼容 edge 不支持 createObjectURL 方法
		if ("msSaveOrOpenBlob" in navigator) return window.navigator.msSaveOrOpenBlob(blob, tempName + fileType)
		const blobUrl = window.URL.createObjectURL(blob)
		const exportFile = document.createElement("a")
		exportFile.style.display = "none"
		exportFile.download = `${tempName}${fileType}`
		exportFile.href = blobUrl
		document.body.appendChild(exportFile)
		exportFile.click()
		// 去除下载对 url 的影响
		document.body.removeChild(exportFile)
		window.URL.revokeObjectURL(blobUrl)
	} catch (error) {
		console.log(error)
	}
}
