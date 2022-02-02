<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Users extends Model
{
    use HasFactory;
    public $timestamps = false;

    protected $table = 'users';

    protected $fillable = [
    'id', 'name', 'image', 'phone', 'email', 'password',
    ];

}