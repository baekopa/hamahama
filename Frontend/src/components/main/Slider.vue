<template>
  <div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide" v-for="(item, index) in items" :key="index">
        {{ item }}
      </div>
    </div>
    <div class="swiper-pagination"></div>
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
  </div>
</template>

<script>
import Swiper from 'swiper'

export default {
  data() {
    return {
      items: ['Slide 1', 'Slide 2', 'Slide 3', 'Slide 4', 'Slide 5'] // 아이템은 필요에 따라 변경 가능합니다.
    }
  },
  mounted() {
    this.swiper = new Swiper('.swiper-container', {
      slidesPerView: 1,
      spaceBetween: 30,
      pagination: {
        el: '.swiper-pagination',
        clickable: true
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev'
      }
    })
  },
  destroyed() {
    if (this.swiper) this.swiper.destroy()
  }
}
</script>

<style>
/* Swiper 스타일 */
.swiper-container {
  width: 100%;
  height: 100%;
}

.swiper-slide {
  text-align: center;
  font-size: 18px;
  background: #fff;
}

.swiper-pagination {
  position: relative;
  bottom: 10px;
}

.swiper-button-next,
.swiper-button-prev {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 30px;
  height: 30px;
  background-color: #000;
  color: #fff;
  border-radius: 50%;
  cursor: pointer;
}

.swiper-button-next {
  right: 10px;
}

.swiper-button-prev {
  left: 10px;
}
</style>
