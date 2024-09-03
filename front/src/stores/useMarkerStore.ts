import { defineStore } from "pinia";

export const useMarkerStore = defineStore({
    id: "marker",
    state: () => ({
        address: "",
        phone: "",
        category: "",
        placeName: "",
    }),
    actions: {
        updateMarkerInfo(info: { address: string; phone: string; category: string; placeName: string }) {
            this.address = info.address;
            this.phone = info.phone;
            this.category = info.category;
            this.placeName = info.placeName;
        },
    },
});
