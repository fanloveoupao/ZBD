package com.tgnet.app.utils.utils;

import android.annotation.TargetApi;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;
import com.tgnet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan-gk on 2017/4/27.
 */

public class AddressBookUtils {
    /**
     * 读取联系人
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public List<AddressBook> getPhoneContact(Context context) throws Exception {
        // 利用内容解析者 获取联系人的数据库
        List<AddressBook> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        //查询手机的联系人
        Cursor cur = resolver
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER,
                                ContactsContract.CommonDataKinds.Phone.TYPE,
                        }, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED asc");
        if (cur == null)
            return new ArrayList<>();
        while (cur.moveToNext()) {
            AddressBook bean = new AddressBook();
            bean.name = cur.getString(1);
            bean.mobile = cur.getString(2)
                    .replaceAll(" ", "")
                    .replaceAll("-", "")
                    .replaceAll("^(\\+86)", "");
            int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cur.getString(idColumn);
            bean.uuid = contactId;
            if (!StringUtil.isNullOrEmpty(bean.name) && !StringUtil.isNullOrEmpty(bean.mobile)) {
                list.add(bean);
            }
        }

        cur.close();
        return list;
    }

    public static Long addAddressData(Context context, String name
            , String mobile, String mobile1, String tel, String tel1, String email, String company, String icon,
                                      String address, String web, String note, String qq, String wx, String fax) throws Exception {
        long contact_id = 0;
        try {
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            contact_id = ContentUris.parseId(resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values));

            Uri uri = ContactsContract.Data.CONTENT_URI;

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, name);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, mobile);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, mobile1);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, tel);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, tel1);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, company);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, address);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, web);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, note);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, qq);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, wx);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, fax);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, email);
            resolver.insert(uri, values);
            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Data.DATA1, icon);
            resolver.insert(uri, values);
            return contact_id;
        } catch (Exception e) {
            if (contact_id == 0) {
                throw new Exception("保存失败，您未开启通讯录读写权限");
            }

        }
        return contact_id;
    }

    public static void updateAddressData(Context context, int contactId, String name, String mobile, String mobile1, String tel, String tel1, String email, String company, String icon
            , String address, String web, String note, String qq, String wx, String fax) throws Exception {
        if (StringUtil.isNullOrEmpty(queryNameFromContactsById(context, contactId))) {
            addAddressData(context, name, mobile, mobile1, tel, tel1, email, company, icon, address, web, note, qq, wx, fax);
            return;
        }
        try {
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.Data.DATA1, mobile)
                    .build());
            if (!StringUtil.isNullOrEmpty(mobile1)) {
                ops.add(ContentProviderOperation
                        .newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(
                                ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                        + ContactsContract.Data.MIMETYPE + "=?",
                                new String[]{String.valueOf(contactId),
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.Data.DATA1, mobile1)
                        .build());
            }

            if (!StringUtil.isNullOrEmpty(tel)) {
                ops.add(ContentProviderOperation
                        .newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(
                                ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                        + ContactsContract.Data.MIMETYPE + "=?",
                                new String[]{String.valueOf(contactId),
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.Data.DATA1, tel)
                        .build());
            }

            if (!StringUtil.isNullOrEmpty(tel1)) {
                ops.add(ContentProviderOperation
                        .newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(
                                ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                        + ContactsContract.Data.MIMETYPE + "=?",
                                new String[]{String.valueOf(contactId),
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.Data.DATA1, tel1)
                        .build());
            }

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, icon)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, address)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Website.URL, web)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, note)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, qq)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, wx)
                    .build());

            ops.add(ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{String.valueOf(contactId),
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, fax)
                    .build());


            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String queryNameFromContactsById(Context context,
                                                   int contactId) {
        String name = null;
        if (contactId != 0) {
            ContentResolver resolver = context.getContentResolver();
            Cursor cur = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.Data.RAW_CONTACT_ID + "="
                            + contactId, null, null);
            while (cur.moveToNext()) {
                int indexName = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                name = cur.getString(indexName);
            }
            if (!cur.isClosed())
                cur.close();
        }
        return name;
    }

    public static class AddressBook {
        @SerializedName("n")
        public String name;//名字
        @SerializedName("c")
        public String company;//公司
        @SerializedName("m")
        public String mobile;//手机
        public long uid;//uid
        public String uuid;

    }

}

