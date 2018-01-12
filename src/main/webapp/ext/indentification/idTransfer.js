/**
 * 根据身份证号返回男女 0 :男   1 ：女
 * @param identification
 * @returns {Number}
 */
function getGender(identification){
	var gender = 0;
	if(identification.length == 15){
		gender = identification.substring(14,15);
	}else if(identification.length == 18){
		gender = identification.substring(16,17);
	}
	return gender%2 == 1 ? 0 : 1; 
}

/**
 * 根据身份证号返回出生日期 1970-1-1
 * @param identification
 */
function getBirthday(identification){
	var birthday = 0;
	if(identification.length == 15){
		birthday = '19' + identification.substring(6,8) + '-' + identification.substring(8,10) + '-' + identification.substring(10,12);
	}else if(identification.length == 18){
		birthday = identification.substring(6,10) + '-' + identification.substring(10,12) + '-' + identification.substring(12,14);
	}
	return birthday;
};