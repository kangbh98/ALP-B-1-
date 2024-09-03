<script setup>
//Vue Material Kit 2 components
import MaterialButton from "@/components/MaterialButton.vue";
</script>
<template>
    <div class="container py-7">
        <div class="row mt-5">
            <div class="col-sm-3 col-6 mx-auto">
                <!-- Button trigger modal -->
                <MaterialButton variant="gradient" color="success" data-bs-toggle="modal" data-bs-target="#exampleModal"> Complete </MaterialButton>

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Your modal title</h5>
                                <MaterialButton color="none" class="btn-close" data-bs-dismiss="modal" aria-label="Close"> </MaterialButton>
                            </div>
                            <div class="modal-body">
                                비밀번호 변경이 완료되었습니다.
                                <br /><br />
                                변경된 비밀번호로 로그인 후 서비스를 이용해 주시길 바랍니다.
                            </div>
                            <div class="modal-footer justify-content-between">
                                <MaterialButton variant="gradient" color="success" class="mb-0"> Move to Login </MaterialButton>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
