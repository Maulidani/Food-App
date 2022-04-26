<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\FoodController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

//user
Route::post('login', 'App\Http\Controllers\UserController@login');
Route::post('register', 'App\Http\Controllers\UserController@register');
Route::post('edit', 'App\Http\Controllers\UserController@edit');
Route::post('edit-image', 'App\Http\Controllers\UserController@editImage');

//food
Route::post('show-food', 'App\Http\Controllers\FoodController@index');
Route::post('show-food-category', 'App\Http\Controllers\FoodController@indexCategory');
Route::post('upload-food', 'App\Http\Controllers\FoodController@upload');
Route::post('edit-food', 'App\Http\Controllers\FoodController@edit');
Route::post('edit-image-food', 'App\Http\Controllers\FoodController@editImage');
Route::post('delete-food', 'App\Http\Controllers\FoodController@delete');