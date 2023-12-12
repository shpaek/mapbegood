<template lang="">
    <div v-show="isModalOpen" class="modal" @click="backClickHandler">
        <div class="search-member" @click.stop>
            <label for="n">닉네임: </label>&nbsp;
            <input type="text" name="nickName" v-model="name" id="n" required
                   placeholder="그룹에 초대하고 싶은 사용자의 닉네임을 입력하세요" />&nbsp;&nbsp;&nbsp;
            <button type="submit" class="search" id="b1">검색</button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'SearchMember',
    props:{
        isModalOpen: Boolean,
    },
    data(){
        return{

        }
    },
    methods:{
        backClickHandler(){
            this.isModalOpen = !this.isModalOpen
        }
    }
}
</script>
<style>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
div.modal>div.search-member{
    background-color: white;
    width: 50%;
    height: 50%
}
</style>