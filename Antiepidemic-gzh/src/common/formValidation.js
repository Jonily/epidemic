/**
 * 表单验证
 **/

const form = {
	//当出现错误时返回错误消息，否则返回空即为验证通过
	/*    // Emoji是否包含表情 // isSpecial 是否包含特殊字符
	 formData:Object 表单对象。{key:value,key:value},key==rules.name
	 rules: Array [{name:name,rule:[],msg:[]},{name:name,rule:[],msg:[]}]
			name:name 属性=> 元素的名称
			rule:字符串数组 ["required","isIdCard","isNum","minLength:9","maxLength:Number"]
			msg:数组 []。 与数组 rule 长度相同,对应的错误提示信息
			 
	*/
	validation: function(formData, rules) {
		for (let item of rules) {
			let key = item.name;
			let rule = item.rule;
			let msgArr = item.msg;
			if (!key || !rule || rule.length === 0 || !msgArr || msgArr.length === 0) {
				continue;
			}
			for (let i = 0, length = rule.length; i < length; i++) {
				let ruleItem = rule[i];
				let msg = msgArr[i];
				if (!ruleItem || !msg) {
					continue;
				}
				//数据处理
				let value = null;
				if (~ruleItem.indexOf(":")) {
					let temp = ruleItem.split(":");
					ruleItem = temp[0];
					value = temp[1];
				}
				let isError = false;
				switch (ruleItem) {
					case "required":
						isError = form._isNullOrEmpty(formData[key]);
						break;
					case "isIdCard":
						isError = !form._isIdCard(formData[key]);
						break;
					case "isNum":
						isError = !form._isNum(formData[key]);
						break;
					case "isNumVal":
						isError = !form._isNumVal(formData[key]);
						break;
					case "isDate":
						isError = !form._isDate(formData[key]);
						break;
					case "isUrl":
						isError = !form._isUrl(formData[key]);
						break;
					case "isSame":
						isError = !form._isSame(formData[key], formData[value]);
						break;
					case "minLength":
						isError = !form._minLength(formData[key], value)
						break;
					case "maxLength":
						isError = !form._maxLength(formData[key], value)
						break;
					case "isTrue":

						isError = !form._isTrue(formData[key])
						break;
					default:
						break;
				}
				if (isError) {
					return msg;
				}
			}
		}
		return "";
	},
	_isNullOrEmpty: function(value) {
		return (value === null || value === '' || value === undefined) ? true : false;
	},
	_isCarNo: function(value) {
		// 新能源车牌
		const xreg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))/;
		// 旧车牌
		const creg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$/;
		if (value.length === 7) {
			return creg.test(value);
		} else if (value.length === 8) {
			return xreg.test(value);
		} else {
			return false;
		}
	},
	_isIdCard: function(value) {
		let idCard = value;
		if (idCard.length == 15) {
			return this.__isValidityBrithBy15IdCard;
		} else if (idCard.length == 18) {
			let arrIdCard = idCard.split("");
			if (this.__isTrueValidateCodeBy18IdCard(arrIdCard) && this.__isValidityBrithBy18IdCard(idCard)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	},
	__isTrueValidateCodeBy18IdCard: function(arrIdCard) {
		let sum = 0;
		let Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
		let ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];

		if (arrIdCard[17].toLowerCase() == 'x') {
			arrIdCard[17] = 10;
		}
		for (let i = 0; i < 17; i++) {
			sum += Wi[i] * arrIdCard[i];
		}
		let valCodePosition = sum % 11;
		if (arrIdCard[17] == ValideCode[valCodePosition]) {
			return true;
		} else {
			return false;
		}
	},
	__isValidityBrithBy18IdCard: function(idCard18) {
		let year = idCard18.substring(6, 10);
		let month = idCard18.substring(10, 12);
		let day = idCard18.substring(12, 14);
		let temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
		if (temp_date.getFullYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() !=
			parseFloat(day)) {
			return false;
		} else {
			return true;
		}
	},
	__isValidityBrithBy15IdCard: function(idCard15) {
		let year = idCard15.substring(6, 8);
		let month = idCard15.substring(8, 10);
		let day = idCard15.substring(10, 12);
		let temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));

		if (temp_date.getYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() !=
			parseFloat(day)) {
			return false;
		} else {
			return true;
		}
	},
	_isNum: function(value) {
		//只能为数字
		// return /^[0-9]+$/.test(value);
		return /^\d$|^-?[1-9]\d+$|^-?\d+\.\d+$/.test(value);
	},
	// 验证是否为数字,值为空则不进行验证
	_isNumVal: function(value) {
		if (value != null && value != '') {
			if (value < 100) return /^\+?((0|([1-9]+\d*))|((0\.\d+)|([1-9]+\d*\.\d+)))$/.test(value);
			return false;
		};
		return true;
	},
	_isDate: function(value) {
		//2019-10-12
		const reg =
			/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
		return reg.test(value);
	},
	_isUrl: function(value) {
		return /^((https?|ftp|file):\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/.test(value);
	},
	_isSame: function(value1, value2) {
		return value1 === value2
	},
	_minLength: function(value, min) {
		return value.length >= Number(min)
	},
	_maxLength: function(value, max) {
		return value.length <= Number(max)
	},
	_isTrue: function(value) {
		if (!typeof value === "boolean") {
			return false
		}
		return value
	}
};
module.exports = {
	validation: form.validation
};
