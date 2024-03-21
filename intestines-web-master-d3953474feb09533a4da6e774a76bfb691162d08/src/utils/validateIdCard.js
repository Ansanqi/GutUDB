export const validateIdCard = (value) => {
	if (value) {
		let iSum = 0
		const year = new Date().getFullYear()
		if (!/^\d{17}(\d|x)$/i.test(value)) {
			return "您输入的身份证长度或格式错误，请重新输入"
		}
		value = value.replace(/x$/i, "a")
		if (aCity[parseInt(value.substr(0, 2))] == null) {
			return "您的身份证地区错误，请重新输入"
		}
		if (aCity[parseInt(value.substr(6, 9))] > year) {
			return "您的身份证年份错误，请重新输入"
		}
		const sBirthday = value.substr(6, 4) + "-" + Number(value.substr(10, 2)) + "-" + Number(value.substr(12, 2))
		const d = new Date(sBirthday.replace(/-/g, "/"))
		if (sBirthday != d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate()) {
			return "身份证上的出生日期错误，请重新输入"
		}
		for (let i = 17; i >= 0; i--) {
			iSum += (Math.pow(2, i) % 11) * parseInt(value.charAt(17 - i), 11)
		}
		if (iSum % 11 != 1) {
			return "您输入的身份证号错误，请重新输入"
		}
		const str = value.substr(value.length - 2, 1)
		if (str % 2 != 0) {
			return "您输入的身份证号性别有误，请重新输入"
		}
		return ""
	} else {
		return "请输入身份证号"
	}
}

const aCity = {
	11: "北京",
	12: "天津",
	13: "河北",
	14: "山西",
	15: "内蒙古",
	21: "辽宁",
	22: "吉林",
	23: "黑龙江",
	31: "上海",
	32: "江苏",
	33: "浙江",
	34: "安徽",
	35: "福建",
	36: "江西",
	37: "山东",
	41: "河南",
	42: "湖北",
	43: "湖南",
	44: "广东",
	45: "广西",
	46: "海南",
	50: "重庆",
	51: "四川",
	52: "贵州",
	53: "云南",
	54: "西藏",
	61: "陕西",
	62: "甘肃",
	63: "青海",
	64: "宁夏",
	65: "新疆",
	71: "台湾",
	81: "香港",
	82: "澳门",
	91: "国外"
}
