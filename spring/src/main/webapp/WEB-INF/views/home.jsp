<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Horizontal Image List with Single Item Navigation</title>
    <style>
        /* 이미지 리스트 컨테이너 */
        .image-slider-container {
            position: relative;
            width: 80%;
            max-width: 800px;
            overflow: hidden;
            margin: 20px auto;
        }

        /* 이미지 리스트 */
        .image-list {
            display: flex;
            transition: transform 0.5s ease;
            list-style: none;
            padding: 10px;
            margin: 0;
            gap: 10px;
        }

        /* 리스트 항목 */
        .image-list-item {
            flex: 0 0 calc(100% / 5 - 10px); /* 한 화면에 5개의 이미지가 보이도록 설정 */
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* 이미지 스타일 */
        .image-list-item img {
            width: 100%;
            display: block;
        }

        /* 좌우 버튼 */
        .slider-button {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background-color: rgba(0, 0, 0, 0.5);
            color: white;
            border: none;
            font-size: 24px;
            padding: 10px;
            cursor: pointer;
            z-index: 1;
        }

        .prev {
            left: 10px;
        }

        .next {
            right: 10px;
        }
    </style>
</head>
<body>

    <div class="image-slider-container">
        <ul class="image-list" id="imageList">
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/FF5733/FFFFFF?text=Image+1" alt="Image 1">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/33FF57/FFFFFF?text=Image+2" alt="Image 2">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/3357FF/FFFFFF?text=Image+3" alt="Image 3">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/FF33A2/FFFFFF?text=Image+4" alt="Image 4">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/FFD700/FFFFFF?text=Image+5" alt="Image 5">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/FF5733/FFFFFF?text=Image+6" alt="Image 6">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/33FF57/FFFFFF?text=Image+7" alt="Image 7">
            </li>
            <li class="image-list-item">
                <img src="https://via.placeholder.com/150x100/3357FF/FFFFFF?text=Image+8" alt="Image 8">
            </li>
        </ul>
        <button class="slider-button prev" onclick="moveSlide(-1)">&#10094;</button>
        <button class="slider-button next" onclick="moveSlide(1)">&#10095;</button>
    </div>

    <script>
        let currentIndex = 0; // 현재 위치

        function moveSlide(direction) {
            const imageList = document.getElementById("imageList");
            const slideWidth = imageList.children[0].offsetWidth + 10; // 슬라이드 너비 + 간격
            const totalItems = imageList.children.length;
            const maxIndex = totalItems - 5; // 5개의 이미지가 한 화면에 보이므로 최대 인덱스를 제한

            // 현재 인덱스 업데이트
            currentIndex += direction;

//             //인덱스 제한 설정
//             if (currentIndex < 0) {
//                 currentIndex = 0;
//             } else if (currentIndex > maxIndex) {
//                 currentIndex = maxIndex;
//             }

            // 슬라이드 이동
            const offset = currentIndex * slideWidth;
            imageList.style.transform = `translateX(-${offset}px)`;
        }
    </script>

</body>
</html>