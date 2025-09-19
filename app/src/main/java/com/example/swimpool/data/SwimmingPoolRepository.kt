package com.example.swimpool.data

import com.example.swimpool.model.SwimmingPool

object SwimmingPoolRepository {

    fun getGuroPools(): List<SwimmingPool> = listOf(
        SwimmingPool(
            name = "구로구민회관 수영장",
            address = "서울 구로구 가마산로25길 9",
            latitude = 37.495821,
            longitude = 126.887098,
            phoneNumber = "02-2620-8800",
            description = "구로구청에서 운영하는 실내 수영장으로 강습과 자유수영을 모두 이용할 수 있습니다."
        ),
        SwimmingPool(
            name = "고척스포츠클럽 수영장",
            address = "서울 구로구 중앙로 18길 53",
            latitude = 37.501532,
            longitude = 126.866752,
            phoneNumber = "02-2680-1119",
            description = "고척동 고척스포츠클럽 내 실내 수영장으로 가족 단위 이용객이 많습니다."
        ),
        SwimmingPool(
            name = "개봉유수지 야외수영장",
            address = "서울 구로구 개봉동 330-6",
            latitude = 37.490727,
            longitude = 126.858521,
            phoneNumber = "02-850-1699",
            description = "여름철에만 개장하는 야외 수영장으로 물놀이 시설을 갖추고 있습니다."
        ),
        SwimmingPool(
            name = "항동 체육센터 수영장",
            address = "서울 구로구 항동 137-20",
            latitude = 37.479478,
            longitude = 126.824953,
            phoneNumber = "02-2066-2600",
            description = "항동 주민을 위한 생활 체육시설로 수영 강습 프로그램을 운영합니다."
        )
    )
}
