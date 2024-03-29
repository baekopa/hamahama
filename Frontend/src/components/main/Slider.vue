<template>
  <v-container class="d-flex justify-center">
    <div class="carousel-container">
      <div class="welcome mb-5 ml-10">
        <div class="">
          <span class="point-font text-3xl point-color">{{ useAuthStore().userName }}</span>
          <span class="text-2xl"> 님은</span>
        </div>
        <span class="text-2xl mt-3"> 오늘도 열공중!</span>
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
            <p>{{ feat.content }}</p>
            <img @click="feat.function" :src="feat.imgUrl" :alt="feat.id" class="carousel__image" />
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
import { ref, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useMainPageStore } from '@/stores/mainPage'
import { Carousel, Slide, Navigation } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'

import noteBasicImage from '@/assets/image/home/NoteBasic.jpg'
import emptynote from '@/assets/image/main/emptynote.jpg'

// 1. 개인스터디 이동
// 2. 최근작성한노트
// 3. 노트 작성

const mainPageStore = useMainPageStore()
const myStudyImg = computed(() => mainPageStore.myStudyImg)

const MainFeat = ref([
  {
    id: 1,
    function: () => mainPageStore.GoMyStudyRoom(mainPageStore.myStudy),
    imgUrl: myStudyImg,
    content: '내 스터디룸'
  },
  {
    id: 2,
    function: () => mainPageStore.GoRecentEditNote(mainPageStore.recentEditNote),
    imgUrl: noteBasicImage,
    content: '최근 노트'
  },
  { id: 3, function: () => mainPageStore.GoCreateNote(), imgUrl: emptynote, content: '노트작성' }
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
</style>
