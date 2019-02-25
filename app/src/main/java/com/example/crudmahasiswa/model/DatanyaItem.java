package com.example.crudmahasiswa.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DatanyaItem implements Parcelable {

	@SerializedName("mahasiswa_jurusan")
	private String mahasiswaJurusan;

	@SerializedName("mahasiswa_nama")
	private String mahasiswaNama;

	@SerializedName("mahasiswa_id")
	private String mahasiswaId;

	@SerializedName("mahasiswa_nim")
	private String mahasiswaNim;

	public void setMahasiswaJurusan(String mahasiswaJurusan){
		this.mahasiswaJurusan = mahasiswaJurusan;
	}

	public String getMahasiswaJurusan(){
		return mahasiswaJurusan;
	}

	public void setMahasiswaNama(String mahasiswaNama){
		this.mahasiswaNama = mahasiswaNama;
	}

	public String getMahasiswaNama(){
		return mahasiswaNama;
	}

	public void setMahasiswaId(String mahasiswaId){
		this.mahasiswaId = mahasiswaId;
	}

	public String getMahasiswaId(){
		return mahasiswaId;
	}

	public void setMahasiswaNim(String mahasiswaNim){
		this.mahasiswaNim = mahasiswaNim;
	}

	public String getMahasiswaNim(){
		return mahasiswaNim;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.mahasiswaJurusan);
		dest.writeString(this.mahasiswaNama);
		dest.writeString(this.mahasiswaId);
		dest.writeString(this.mahasiswaNim);
	}

	public DatanyaItem() {
	}

	protected DatanyaItem(Parcel in) {
		this.mahasiswaJurusan = in.readString();
		this.mahasiswaNama = in.readString();
		this.mahasiswaId = in.readString();
		this.mahasiswaNim = in.readString();
	}

	public static final Parcelable.Creator<DatanyaItem> CREATOR = new Parcelable.Creator<DatanyaItem>() {
		@Override
		public DatanyaItem createFromParcel(Parcel source) {
			return new DatanyaItem(source);
		}

		@Override
		public DatanyaItem[] newArray(int size) {
			return new DatanyaItem[size];
		}
	};
}