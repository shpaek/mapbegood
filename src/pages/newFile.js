import axios from 'axios';

export default (await import('vue')).defineComponent({
name: "ThememapCreate",
data() {
return {
colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
themeName: '',
selectedColor: '',
themeMemo: '',
isThemePublic: false,
isMainMap: false,
favoriteList: [],
};
},
methods: {
createThemeMap() {
// 사용자 입력을 이용해 themeMapDto 객체 생성
const themeMapDto = {
name: this.themeName,
color: this.selectedColor,
memo: this.themeMemo,
show: this.isThemePublic,
mainmap: this.isMainMap,
};

const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
const url = `${this.backURL}/mymap/create`;

axios.defaults.headers.common["Authorization"] = accessToken;

axios.post(url, themeMapDto)
.then(response => {
console.log(response.data);
// 성공적으로 생성되었을 때의 로직 추가
alert("thememap이 성공적으로 생성되었습니다.");
// Thememap.vue로 자동으로 이동
this.$router.push({ name: 'FavoriteList' });
})
.catch(error => {
console.error(error);
alert("thememap 생성에 실패했습니다. 다시 시도하세요.");
});
}, // 복사 메서드 추가
copyThememap(themeMapId) {
// 선택한 테마맵을 복사하기 위한 서버 요청
const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
const url = `${this.backURL}/mymap/copy/${themeMapId}`;

axios.defaults.headers.common["Authorization"] = accessToken;

axios.post(url)
.then(response => {
console.log(response.data);
// 성공적으로 복사되었을 때의 로직 추가
alert("테마맵이 성공적으로 복사되었습니다.");
// Thememap.vue로 자동으로 이동
this.$router.push({ name: 'FavoriteList' });
})
.catch(error => {
console.error(error);
alert("테마맵 복사에 실패했습니다. 다시 시도하세요.");
});
},
},
});
