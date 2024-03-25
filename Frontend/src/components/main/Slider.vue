<template>
  <v-container class="d-flex justify-center">
    <div class="carousel-container">
      <div class="welcome mb-5 ml-10">
        <div class="">
          <span class="name-font text-3xl text-cyan-500">{{ useAuthStore().userName }}</span>
          <span class="text-2xl"> 님은</span>
        </div>
        <span class="text-2xl mt-3 "> 오늘도 열공중!</span>
      </div>
      <Carousel
        :items-to-show="2"
        :wrapAround="true"
        :transition="500"
        :autoplay="2000"
        :buttonVisibility="true"
      >
        <Slide v-for="feat in MainFeat" :key="feat.id">
          <div class="carousel__item">
            <img :src="feat.imgUrl" :alt="feat.id" class="carousel__image" />
          </div>
        </Slide>
        <template #addons>
          <Navigation />
        </template>
      </Carousel>
    </div>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { Carousel, Slide, Navigation } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'

import Main_1 from '../../assets/image/main/karina.jpg'
import Main_2 from '../../assets/image/main/irean.jpg'
import Main_3 from '../../assets/image/main/ajeong.png'

const MainFeat = ref([
  { id: 1, imgUrl: Main_1 },
  { id: 2, imgUrl: Main_2 },
  { id: 3, imgUrl: Main_3 }
])
</script>

<style scoped>
.welcome {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: start;
  align-self: start;
}
.carousel {
  width: 1300px;
}
.container {
  display: flex;
  flex-direction: column;
  justify-content: center; /* 중앙 정렬을 위한 추가 */
  align-items: center;
}
.carousel-container {
  width: 1300px;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 중앙 정렬을 위한 추가 */
  align-items: center;
}
.carousel__item {
  width: 900px;
  height: 400px;

  object-fit: cover; /* 이미지 비율 유지 및 잘림 방지 */
}

.carousel__viewport {
  perspective: 1000px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}

.carousel__slide {
  opacity: 1;
  transform: rotateY(0);
}

.carousel__slide--active ~ .carousel__slide {
  transform: rotateY(0) scale(0.9);
}

.carousel__slide--prev {
  opacity: 0.3;
  transform: rotateY(30deg) scale(0.9);
}

.carousel__slide--next {
  opacity: 0.3;
  transform: rotateY(0);
}

.carousel__slide--active {
  opacity: 1;
  transform: rotateY(0) scale(1);
  z-index: 100;
}

.carousel__image {
  width: 800px;
  height: 100%;
  border-radius: 20px;
  object-fit: cover; /* 이미지 비율 유지 및 잘림 방지 */
}

.name-font {
  font-family: "UhBeeSe_hyun";
}
</style>
