package com.example.expandablelv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.expandablelv.Model.Category;
import com.example.expandablelv.Model.Service;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExpandLVAdapter extends BaseExpandableListAdapter {

    private List<Category> listCate;
    private Map<Category, List<Service>> listService;

    public ExpandLVAdapter(List<Category> listCate, Map<Category, List<Service>> listService) {
        this.listCate = listCate;
        this.listService = listService;
    }

    @Override
    public int getGroupCount() {
        if (listCate != null){
            return listCate.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listCate != null && listService != null){
            return listService.get(listCate.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCate.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listService.get(listCate.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Category category = listCate.get(groupPosition);
        return category.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Service service = listService.get(listCate.get(groupPosition)).get(childPosition);
        return service.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent, false);
        }
        TextView txtCate = convertView.findViewById(R.id.petCate);
        Category category = listCate.get(groupPosition);
        txtCate.setText(category.getName().toUpperCase());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        }
        TextView txtService = (TextView) convertView.findViewById(R.id.petService);
        Service service = listService.get(listCate.get(groupPosition)).get(childPosition);
        txtService.setText(service.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
