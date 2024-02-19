package com.spenditure.presentation.category;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spenditure.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.presentation.IOnDialogCloseListener;

public class AddNewCategory extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewCategory";
    private EditText mCategoryEdit;
    private Button mSaveBtn;
    private CategoryHandler categoryHandler;
    private Context context;

    public static AddNewCategory newInstance(){
        return new AddNewCategory();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_category,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoryEdit = view.findViewById(R.id.category_edittext);
        mSaveBtn = view.findViewById(R.id.category_save_btn);
        this.categoryHandler = new CategoryHandler(true);

        mCategoryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    mSaveBtn.setEnabled(false);
                    mSaveBtn.setBackgroundColor(Color.GRAY);
                }else{
                    mSaveBtn.setEnabled(true);
                    mSaveBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategory = mCategoryEdit.getText().toString();
                if(newCategory.isEmpty()){
                    Toast.makeText(context,"Empty Category not Allowed", Toast.LENGTH_SHORT).show();

                }else{
                    categoryHandler.addCategory(newCategory);
                    Toast.makeText(context,"Category added successfully", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof IOnDialogCloseListener){
            ((IOnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
