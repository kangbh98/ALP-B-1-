<template>
    <KakaoMap ref="mapRef" :lat="37.566826" :lng="126.9786567" :level="5" :draggable="true" width="100vw" height="100vh" @onLoadKakaoMap="onLoadKakaoMap">
        <KakaoMapMarker
            v-for="(marker, index) in markerList"
            :key="marker.key === undefined ? index : marker.key"
            :lat="marker.lat"
            :lng="marker.lng"
            :infoWindow="marker.infoWindow"
            :clickable="true"
            title="상대경로로 이미지 가져오기"
            :image="{
                imageSrc: '/public/favicon.png',
                imageWidth: 50,
                imageHeight: 50,
                imageOption: {},
            }"
            @onClickKakaoMapMarker="onClickMapMarker(marker)"
        />
    </KakaoMap>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { KakaoMap, KakaoMapMarker, type KakaoMapMarkerListItem } from "vue3-kakao-maps";
import { useMarkerStore } from "@/stores/useMarkerStore";

const props = defineProps({
    searchKeyword: String,
});

const emits = defineEmits(["placeName"]);

const mapRef = ref(null);
const map = ref<kakao.maps.Map>();
const markerList = ref<KakaoMapMarkerListItem[]>([]);
const markerStore = useMarkerStore();

onMounted(() => {
    const mapElement = mapRef.value.$el;
    mapElement.style.position = "fixed";
    mapElement.style.top = "0";
    mapElement.style.left = "0";
    mapElement.style.width = "100vw";
    mapElement.style.height = "100vh";
    mapElement.style.zIndex = "-1";
    mapElement.style.filter = "grayscale(10%) contrast(90%)";
});

const onLoadKakaoMap = (mapInstance: kakao.maps.Map) => {
    map.value = mapInstance;
    searchPlaces(props.searchKeyword);
};

watch(
    () => props.searchKeyword,
    (newKeyword) => {
        if (map.value) {
            searchPlaces(newKeyword);
        }
    }
);

const searchPlaces = (keyword: string) => {
    const ps = new kakao.maps.services.Places();
    ps.keywordSearch(keyword, placesSearchCB);
};

const placesSearchCB = (data: kakao.maps.services.PlacesSearchResult, status: kakao.maps.services.Status): void => {
    if (status === kakao.maps.services.Status.OK) {
        const bounds = new kakao.maps.LatLngBounds();
        markerList.value = [];

        for (let marker of data) {
            const markerItem: KakaoMapMarkerListItem = {
                lat: Number(marker.y),
                lng: Number(marker.x),
                infoWindow: {
                    content: marker.address_name + "\n" + marker.phone + "\n" + marker.category_group_name + "\n" + marker.place_name,
                    visible: false,
                },
            };
            markerList.value.push(markerItem);
            bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
        }
        map.value?.setBounds(bounds, 100, 100, 100, 100);
    }
};

const onClickMapMarker = (markerItem: KakaoMapMarkerListItem): void => {
    const details = markerItem.infoWindow.content.split("\n");
    if (details.length === 4) {
        markerStore.updateMarkerInfo({
            address: details[0],
            phone: details[1],
            category: details[2],
            placeName: details[3],
        });
        emits("placeName", details[3]);
    }
};
</script>

<style scoped></style>
