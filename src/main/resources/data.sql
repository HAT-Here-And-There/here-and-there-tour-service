do
$$
    begin
        if (select count(*) FROM major_region) = 0 then
            insert into major_region(name, image_url)
            values ('서울',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('경기',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('인천',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('강원',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('충청',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('전라',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('경상',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA'),
                   ('제주',
                    'https://www.google.com/imgres?q=seoul%20tour&imgurl=https%3A%2F%2Fmedia.tacdn.com%2Fmedia%2Fattractions-splice-spp-674x446%2F06%2F6b%2F64%2Ffc.jpg&imgrefurl=https%3A%2F%2Fwww.viator.com%2Ftours%2FSeoul%2FFull-Day-Seoul-Tour-Including-Lotte-World%2Fd973-14882P12&docid=eDhLRKJzxcxVwM&tbnid=iqsnOAvDtKsT-M&vet=12ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA..i&w=669&h=446&hcb=2&ved=2ahUKEwieipHb6rqGAxXik68BHbm6BX8QM3oECBoQAA');
        end if;

        if (select count(*) from sigungu s where s.major_region_id is not null) = 0 then
            update sigungu
            set major_region_id = case
                                      when area_id = '1' then 1
                                      when area_id = '31' then 2
                                      when area_id = '2' then 3
                                      when area_id = '32' then 4
                                      when area_id in ('3', '8', '33', '34') then 5
                                      when area_id in ('5', '37', '38') then 6
                                      when area_id in ('4', '6', '7', '35', '36') then 7
                                      when area_id in ('39') then 8
                end
            where major_region_id is null;
        end if;
    end
$$;;
